package org.tenkiv.coral

import io.kotlintest.eventually
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.seconds
import io.kotlintest.specs.StringSpec
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class UnimutSpec : StringSpec({

    "unimut property should throw exception if accessed before initialized" {
        var noSafety: Int by unimut(UniMutConcurrencyMode.NONE)
        var blocking: Int by unimut(UniMutConcurrencyMode.BLOCKING)

        shouldThrow<UninitializedPropertyAccessException> { println(noSafety) }
        thread(start = true) { println(blocking) }
    }

    "unimut property should throw exception if set multiple times" {
        var noSafety: Int by unimut(UniMutConcurrencyMode.NONE)

        shouldThrow<AlreadySetException> {
            noSafety = 1
            noSafety = 2
            noSafety
        }

        var publication: Int by unimut(UniMutConcurrencyMode.PUBLICATION)

        eventually(2.seconds) {
            val exceptionLock = ReentrantLock()
            var firstSet = true
            var exception: AlreadySetException? = null

            thread(start = true) {
                try {
                    publication = 1 + 1 + 1 + 1 + 1 + 1 + 1 + 2
                } catch (e: AlreadySetException) {
                    exceptionLock.withLock {
                        exception = e
                    }
                }
                exceptionLock.withLock {
                    if (firstSet) {
                        exception shouldBe null
                        firstSet = false
                    } else
                        exception shouldNotBe null
                }
            }
            thread(start = true) {
                try {
                    publication = 2 + 2
                } catch (e: AlreadySetException) {
                    exceptionLock.withLock {
                        exception = e
                    }
                }
                exceptionLock.withLock {
                    exceptionLock.withLock {
                        if (firstSet) {
                            exception shouldBe null
                            firstSet = false
                        } else
                            exception shouldNotBe null
                    }
                }
            }

        }


        var synchronized: Int by unimut(UniMutConcurrencyMode.SYNCHRONIZED)

        eventually(2.seconds) {
            thread(start = true) {
                synchronized = 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1
            }
            thread(start = true) {
                shouldThrow<AlreadySetException> {
                    synchronized = 1
                    synchronized
                }
            }
        }

        var blocking: Int by unimut(UniMutConcurrencyMode.BLOCKING)

        eventually(2.seconds) {
            thread(start = true) {
                blocking = 1 + 1
            }
            thread(start = true) {
                shouldThrow<AlreadySetException> {
                    blocking = 2 + 1
                    blocking
                }
            }
        }

    }

    "unimut property should initialise and read correctly" {
        val valueOne = "HELLO"
        var noSafety: String by unimut(UniMutConcurrencyMode.NONE,
            onGet = {
                it shouldEqual valueOne
            },
            onSet = {
                it shouldEqual valueOne
            })
        noSafety = valueOne
        noSafety shouldEqual valueOne

        var publication: String by unimut(UniMutConcurrencyMode.PUBLICATION,
            onGet = {
                it shouldEqual valueOne
            },
            onSet = {
                it shouldEqual valueOne
            })

        eventually(2.seconds) {
            thread(start = true) {
                publication = valueOne
            }

            thread(start = true) {
                publication shouldEqual valueOne
            }
        }

        var synchronised: String by unimut(UniMutConcurrencyMode.SYNCHRONIZED,
            onGet = {
                it shouldEqual valueOne
            },
            onSet = {
                it shouldEqual valueOne
            })

        eventually(2.seconds) {
            thread(start = true) {
                synchronised = valueOne
            }

            thread(start = true) {
                synchronised shouldEqual valueOne
            }
        }

        var blocking: String by unimut(UniMutConcurrencyMode.BLOCKING,
            onGet = {
                it shouldEqual valueOne
            },
            onSet = {
                it shouldEqual valueOne
            })

        eventually(2.seconds) {
            thread(start = true) {
                blocking shouldEqual valueOne
            }

            thread(start = true) {
                Thread.sleep(1000)
                blocking = valueOne
            }
        }
    }

})