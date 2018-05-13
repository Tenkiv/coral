package org.tenkiv.coral


import io.kotlintest.eventually
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrow
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

        eventually(2L.secondsSpan) {
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

        eventually(2L.secondsSpan) {
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

        eventually(2L.secondsSpan) {
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
                it shouldBe valueOne
            },
            onSet = {
                it shouldBe valueOne
            })
        noSafety = valueOne
        noSafety shouldBe valueOne

        var publication: String by unimut(UniMutConcurrencyMode.PUBLICATION,
            onGet = {
                it shouldBe valueOne
            },
            onSet = {
                it shouldBe valueOne
            })

        eventually(2L.secondsSpan) {
            thread(start = true) {
                publication = valueOne
            }

            thread(start = true) {
                publication shouldBe valueOne
            }
        }

        var synchronised: String by unimut(UniMutConcurrencyMode.SYNCHRONIZED,
            onGet = {
                it shouldBe valueOne
            },
            onSet = {
                it shouldBe valueOne
            })

        eventually(2L.secondsSpan) {
            thread(start = true) {
                synchronised = valueOne
            }

            thread(start = true) {
                synchronised shouldBe valueOne
            }
        }

        var blocking: String by unimut(UniMutConcurrencyMode.BLOCKING,
            onGet = {
                it shouldBe valueOne
            },
            onSet = {
                it shouldBe valueOne
            })

        eventually(2L.secondsSpan) {
            thread(start = true) {
                blocking shouldBe valueOne
            }

            thread(start = true) {
                Thread.sleep(1000)
                blocking = valueOne
            }
        }
    }

})