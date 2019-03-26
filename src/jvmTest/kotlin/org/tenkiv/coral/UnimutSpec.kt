/*
 * Copyright 2019 Tenkiv, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.tenkiv.coral

import org.spekframework.spek2.*
import org.spekframework.spek2.style.specification.*
import kotlin.test.*

object UnimutSpec : Spek({
    describe("unimut property should throw exception if accessed before initialized") {
        val noSafety: Int by unimut(UniMutConcurrencyMode.NONE)
        val blocking: Int by unimut(UniMutConcurrencyMode.BLOCKING)
        //TODO: add blocking test

        it("") {
            assertFailsWith(UninitializedPropertyAccessException::class) { println(noSafety) }
        }
    }

    describe("unimut property should throw exception if set multiple times") {
        var noSafety: Int by unimut(UniMutConcurrencyMode.NONE)

        it("") {
            assertFailsWith(AlreadySetException::class) {
                noSafety = 1
                noSafety = 2
                noSafety
            }
        }

        //TODO: add Spek equivalent of KotlinTest 'eventually' tests when Spek 2.1 is released
//        var publication: Int by unimut(UniMutConcurrencyMode.PUBLICATION)

//        eventually(2L.secondsSpan) {
//            val exceptionLock = ReentrantLock()
//            var firstSet = true
//            var exception: AlreadySetException? = null
//
//            thread(start = true) {
//                try {
//                    publication = 1 + 1 + 1 + 1 + 1 + 1 + 1 + 2
//                } catch (e: AlreadySetException) {
//                    exceptionLock.withLock {
//                        exception = e
//                    }
//                }
//                exceptionLock.withLock {
//                    if (firstSet) {
//                        exception shouldBe null
//                        firstSet = false
//                    } else
//                        exception shouldNotBe null
//                }
//            }
//            thread(start = true) {
//                try {
//                    publication = 2 + 2
//                } catch (e: AlreadySetException) {
//                    exceptionLock.withLock {
//                        exception = e
//                    }
//                }
//                exceptionLock.withLock {
//                    exceptionLock.withLock {
//                        if (firstSet) {
//                            exception shouldBe null
//                            firstSet = false
//                        } else
//                            exception shouldNotBe null
//                    }
//                }
//            }
//
//        }

//        var synchronized: Int by unimut(UniMutConcurrencyMode.SYNCHRONIZED)
//
//        eventually(2L.secondsSpan) {
//            thread(start = true) {
//                synchronized = 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1
//            }
//            thread(start = true) {
//                shouldThrow<AlreadySetException> {
//                    synchronized = 1
//                    synchronized
//                }
//            }
//        }
//
//        var blocking: Int by unimut(UniMutConcurrencyMode.BLOCKING)
//
//        eventually(2L.secondsSpan) {
//            thread(start = true) {
//                blocking = 1 + 1
//            }
//            thread(start = true) {
//                shouldThrow<AlreadySetException> {
//                    blocking = 2 + 1
//                    blocking
//                }
//            }
//        }

    }

    describe("unimut property should initialise and read correctly") {
        val valueOne = "HELLO"

        it("") {
            var noSafety: String by unimut(UniMutConcurrencyMode.NONE,
                onGet = {
                    assertEquals(valueOne, it)
                },
                onSet = {
                    assertEquals(valueOne, it)
                })

            noSafety = valueOne
            assertEquals(valueOne, noSafety)
        }

        it("") {
            var publication: String by unimut(UniMutConcurrencyMode.PUBLICATION,
                onGet = {
                    assertEquals(valueOne, it)
                },
                onSet = {
                    assertEquals(valueOne, it)
                })

            //TODO: add Spek equivalent of KotlinTest 'eventually' tests when Spek 2.1 is released
//            eventually(2L.secondsSpan) {
//                thread(start = true) {
//                    publication = valueOne
//                }
//
//                thread(start = true) {
//                    publication shouldBe valueOne
//                }
//            }
        }

        it("") {
            var synchronised: String by unimut(UniMutConcurrencyMode.SYNCHRONIZED,
                onGet = {
                    assertEquals(valueOne, it)
                },
                onSet = {
                    assertEquals(valueOne, it)
                })
            //TODO: add Spek equivalent of KotlinTest 'eventually' tests when Spek 2.1 is released
//            eventually(2L.secondsSpan) {
//                thread(start = true) {
//                    synchronised = valueOne
//                }
//
//                thread(start = true) {
//                    synchronised shouldBe valueOne
//                }
//            }
        }

        it("") {
            var blocking: String by unimut(UniMutConcurrencyMode.BLOCKING,
                onGet = {
                    assertEquals(valueOne, it)
                },
                onSet = {
                    assertEquals(valueOne, it)
                })

            //TODO: add Spek equivalent of KotlinTest 'eventually' tests when Spek 2.1 is released
//            eventually(2L.secondsSpan) {
//                thread(start = true) {
//                    blocking shouldBe valueOne
//                }
//
//                thread(start = true) {
//                    Thread.sleep(1000)
//                    blocking = valueOne
//                }
//            }
        }
    }
})