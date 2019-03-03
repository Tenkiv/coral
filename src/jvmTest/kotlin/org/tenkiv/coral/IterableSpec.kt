/*
 * Copyright 2018 Tenkiv, Inc.
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
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

private val emptyCollection: List<Double> = emptyList()
private val doubleCollection = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)
private val booleanCollection = listOf(true, false, true, false, true, false, true, false)

class IterableAverageSpec : StringSpec({

    "average an empty collection" {
        emptyCollection.average { it }.isNaN() shouldBe true
    }

    "average of all elements in test collection should be 5.5" {
        doubleCollection.average { it }.feq(5.5, 0.000001) shouldBe true
    }

    "average of all even elements in test collection should be 6" {
        doubleCollection.average(
            getValue = { it },
            condition = { it % 2.0 feq 0.0 }
        ).feq(6.0, 0.000001) shouldBe true
    }

})

class IterableRatioSpec : StringSpec({

    "ratio of sum of even doubles to odd doubles should be 1.2" {
        doubleCollection.ratio(
            of = { if (it % 2.0 feq 0.0) it else 0.0 },
            to = { if (it % 2.0 feq 0.0) 0.0 else it }
        ).feq(1.2, 0.000001) shouldBe true
    }

    "ratio of true to false should be 1" {
        booleanCollection.booleanRatio(
            of = { it },
            to = { it }
        ).feq(1.0, 0.000001) shouldBe true
    }

    "ratio of sum of even doubles to total should be 0.5454" {
        doubleCollection.ratioToTotal(
            of = { if (it % 2.0 feq 0.0) it else 0.0 },
            other = { if (it % 2.0 feq 0.0) 0.0 else it }
        ).feq(0.545454545454545454, 0.0001) shouldBe true
    }

    "ratio of true to total should be 0.5" {
        booleanCollection.booleanRatioToTotal(
            of = { it },
            other = { it }
        ).feq(0.5, 0.000001) shouldBe true
    }

})