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

class EqualDoubleTests {
    val equalDoubles = listOf(
        0.0 to 0.0,
        (0.15 + 0.15) to (0.1 + 0.2),
        (1_000.0 / 100) to 10.0,
        (1_000.1 - 1_000) to 0.1
    )

    @Test
    fun default_feq() = equalDoubles.forEach { equalDoublesPair ->
        assertTrue(equalDoublesPair.first feq equalDoublesPair.second)
    }

}

class NotEqualDoublesTests {
    val notEqualDoubles = listOf(
        1_000_000_000.0 to 1_000_000_000.001,
        0.0000000000000000002 to 0.0000000000000000001
    )

    @Test
    fun default_feq() {
        notEqualDoubles.forEach {
            assertFalse(it.first feq it.second)
        }
    }

}

class ApproximatelyEqualDoublesEpsilonTests {
    val first = 1_000_000_000.0001
    val second = 1_000_000_000.0002

    @Test
    fun feq_with_epsilon_provided_that_is_smaller_than_the_difference_between_the_numbers_being_compared() {
        assertFalse(first.feq(second, 0.00001))
    }

    @Test
    fun feq_with_epsilon_provided_that_is_larger_than_the_difference_between_the_numbers_being_compared() {
        assertTrue(first.feq(second, 0.01))
    }

}

class ApproximatelyEqualDoublesUlpTests {
    val first = 1_000_000_000.0000001
    val second = 1_000_000_000.0000003

    @Test
    fun feq_with_maxUlps_provided_that_is_smaller_than_the_number_of_ulps_between_the_numbers() {
        assertFalse(first.feq(second, 1))
    }

    @Test
    fun feq_with_maxUlps_provided_that_is_greater_than_the_number_of_ulps_between_the_numbers() {
        assertTrue(first.feq(second, 2))
    }

}