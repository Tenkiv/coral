package org.tenkiv.coral

import io.kotlintest.forAll
import io.kotlintest.matchers.shouldBe
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
        //TODO
        val first = 1_000_000_000.0001
        val second = 1_000_000_000.0002

        first.feq(second, 1) shouldBe false
        first.feq(second, 1) shouldBe true
    }

    "floats should have equality approximated according to defaults" {

    }

    "floats should have equality approximated according to non-default epsilon" {

    }

    "floats should have equality approximated according to non-default max ULPs" {

    }
})