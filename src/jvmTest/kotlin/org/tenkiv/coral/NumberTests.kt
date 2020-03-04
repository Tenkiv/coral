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

class EqualFloatsTests {
    val equalFloats = listOf(
        0.0f to 0.0f,
        (0.15f + 0.15f) to (0.1f + 0.2f),
        (1_000.0f / 100f) to 10.0f,
        (100.5f - 100f) to 0.5f
    )

    @Test
    fun `default feq`() {
        equalFloats.forEach { equalFloatsPair ->
            assertTrue(equalFloatsPair.first feq equalFloatsPair.second)
        }
    }

}

class NotEqualFloatTests {
    val notEqualFloats = listOf(
        60_000f to 60_001f,
        0.00000000002f to 0.00000000001f
    )

    @Test
    fun `default feq`() {
        notEqualFloats.forEach { notEqualFloatsPair ->
            assertFalse(notEqualFloatsPair.first feq notEqualFloatsPair.second)
        }
    }

}

class ApproximatelyEqualFloatEpsilonTests {
    val first = 1_000.01f
    val second = 1_000.02f

    @Test
    fun `feq with epsilon provided that is smaller than the difference between the numbers being compared`() {
        assertFalse(first.feq(second, 0.001f))
    }

    @Test
    fun `feq with epsilon provided that is larger than the difference between the numbers being compared`() {
        assertTrue(first.feq(second, 0.1f))
    }

}

class ApproximatelyEqualFloatUlpTests {
    val first = 1_000_000.0625f
    val second = 1_000_000.19f

    @Test
    fun `feq with epsilon provided that is smaller than the difference between the numbers being compared`() {
        assertFalse(first.feq(second, 1))
    }

    @Test
    fun `feq with epsilon provided that is larger than the difference between the numbers being compared`() {
        assertTrue(first.feq(second, 2))
    }

}