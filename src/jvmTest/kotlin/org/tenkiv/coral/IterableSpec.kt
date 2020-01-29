/*
 * Copyright 2020 Tenkiv, Inc.
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

val emptyCollection: List<Double> = emptyList()
val doubleCollection = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
val booleanCollection = listOf(true, false, true, false, true, false, true, false)

object IterableAverageSpec : Spek({

    describe("calls average on an empty collection") {
        it("returns true if empty collection is NaN") {
            assertEquals(true, emptyCollection.average { it }.isNaN())
        }
    }

    describe("calls average on a collection of doubles") {
        it("returns true if averaged collection value is equal to a float of correct value") {
            assertEquals(true, doubleCollection.average { it }.feq(5.5, 0.00001))
        }
    }

    describe("calls average on a collection of doubles with a condition") {
        it("returns true if averaged collection value with given condition is equal to a float of the correct value") {
            assertEquals(true, doubleCollection.average(
                getValue = { it },
                condition = { it % 2.0 feq 0.0 }
            ).feq(6.0, 0.000001))
        }
    }
})

object IterableRatioSpec : Spek({

    describe("calls ratio on a collection of doubles") {
        it("returns true if ratio of sun of even doubles to odd doubles is equal to a float of correct value") {
            assertEquals(true, doubleCollection.ratio(
                of = { if (it % 2.0 feq 0.0) it else 0.0 },
                to = { if (it % 2.0 feq 0.0) 0.0 else it }
            ).feq(1.2, 0.000001))
        }
    }

    describe("calls booleanRatio on a collection of booleans") {
        it("returns true if ratio of true to false is equal to a float of the correct value") {
            assertEquals(true, booleanCollection.booleanRatio(
                of = { it },
                to = { it }
            ).feq(1.0, 0.000001))
        }
    }

    describe("calls ratioToTotal on a collection of doubles") {
        it("returns true if ratio of the sum of even doubles to total doubles is equal to a float of correct value") {
            doubleCollection.ratioToTotal(
                of = { if (it % 2.0 feq 0.0) it else 0.0 },
                other = { if (it % 2.0 feq 0.0) 0.0 else it }
            ).feq(0.545454545454545454, 0.0001)
        }
    }

    describe("calls booleanRatioToTotal on a collection of booleans") {
        it("returns true if ratio of true to total booleans is equal to a float of the correct value") {
            booleanCollection.booleanRatioToTotal(
                of = { it },
                other = { it }
            ).feq(0.5, 0.000001)
        }
    }
})