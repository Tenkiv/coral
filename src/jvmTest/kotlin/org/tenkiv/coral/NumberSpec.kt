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
import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class FeqSpec : StringSpec({

    "doubles should have equality approximated according to defaults" {
        val equalDoubles = listOf(
            0.0 to 0.0,
            (0.15 + 0.15) to (0.1 + 0.2),
            (1_000.0 / 100) to 10.0,
            (1_000.1 - 1_000) to 0.1
        )

        forAll(equalDoubles) {
            it.first feq it.second shouldBe true
        }

        val notEqualDoubles = listOf(
            1_000_000_000.0 to 1_000_000_000.001,
            0.0000000000000000002 to 0.0000000000000000001
        )

        forAll(notEqualDoubles) {
            it.first feq it.second shouldBe false
        }

    }

    "doubles should have equality approximated according to non-default epsilon" {
        val first = 1_000_000_000.0001
        val second = 1_000_000_000.0002

        first.feq(second, 0.00001) shouldBe false
        first.feq(second, 0.01) shouldBe true
    }

    "doubles should have equality approximated according to non-default max ULPs" {
        val first = 1_000_000_000.0000001
        val second = 1_000_000_000.0000003

        first.feq(second, 1) shouldBe false
        first.feq(second, 2) shouldBe true
    }

    "floats should have equality approximated according to defaults" {
        val equalFloats = listOf(
            0.0f to 0.0f,
            (0.15f + 0.15f) to (0.1f + 0.2f),
            (1_000.0f / 100f) to 10.0f,
            (100.5f - 100f) to 0.5f
        )

        forAll(equalFloats) {
            it.first feq it.second shouldBe true
        }

        val notEqualFloats = listOf(
            60_000f to 60_001f,
            0.00000000002f to 0.00000000001f
        )

        forAll(notEqualFloats) {
            it.first feq it.second shouldBe false
        }
    }

    "floats should have equality approximated according to non-default epsilon" {
        val first = 1_000.01f
        val second = 1_000.02f

        first.feq(second, 0.001f) shouldBe false
        first.feq(second, 0.1f) shouldBe true
    }

    "floats should have equality approximated according to non-default max ULPs" {
        val first = 1_000_000.0625f
        val second = 1_000_000.19f

        first.feq(second, 1) shouldBe false
        first.feq(second, 2) shouldBe true
    }
})