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

@file:Suppress("PublicApiImplicitType")

package org.tenkiv.coral

import kotlin.test.*

class DoubleCollectionTests {
    val doubleCollection = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0)

    @Test
    fun average() {
        assertTrue(doubleCollection.average { it }.feq(5.5, 0.00001))
    }

    @Test
    fun ratio() {
        assertTrue(
            doubleCollection.ratio(
                of = { if (it % 2.0 feq 0.0) it else 0.0 },
                to = { if (it % 2.0 feq 0.0) 0.0 else it }
            ).feq(1.2, 0.000001)
        )
    }

    @Test
    fun ratioToTotal() {
        doubleCollection.ratioToTotal(
            of = { if (it % 2.0 feq 0.0) it else 0.0 },
            other = { if (it % 2.0 feq 0.0) 0.0 else it }
        ).feq(0.545454545454545454, 0.0001)
    }

}

class BooleanCollectionTests {
    val booleanCollection = listOf(true, false, true, false, true, false, true, false)

    @Test
    fun booleanRatio() {
        assertTrue(
            booleanCollection.booleanRatio(
                of = { it },
                to = { it }
            ).feq(1.0, 0.000001)
        )
    }

    @Test
    fun serializationRatioToTotal() {
        assertTrue(
            booleanCollection.booleanRatioToTotal(
                of = { it },
                other = { it }
            ).feq(0.5, 0.000001)
        )
    }

}

class EmptyCollectionTests {
    val emptyCollection: List<Double> = emptyList()

    @Test
    fun average() {
        assertTrue(emptyCollection.average { it }.isNaN())
    }

}