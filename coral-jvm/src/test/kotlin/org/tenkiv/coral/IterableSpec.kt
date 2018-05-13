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
        doubleCollection.findRatio(
            of = { if (it % 2.0 feq 0.0) it else 0.0 },
            to = { if (it % 2.0 feq 0.0) 0.0 else it }
        ).feq(1.2, 0.000001) shouldBe true
    }

    "ratio of true to false should be 1" {
        booleanCollection.findBooleanRatio(
            of = { it },
            to = { it }
        ).feq(1.0, 0.000001) shouldBe true
    }

    "ratio of sum of even doubles to total should be 0.5454" {
        doubleCollection.findRatioToTotal(
            of = { if (it % 2.0 feq 0.0) it else 0.0 },
            other = { if (it % 2.0 feq 0.0) 0.0 else it }
        ).feq(0.545454545454545454, 0.0001) shouldBe true
    }

    "ratio of true to total should be 0.5" {
        booleanCollection.findBooleanRatioToTotal(
            of = { it },
            other = { it }
        ).feq(0.5, 0.000001) shouldBe true
    }

})