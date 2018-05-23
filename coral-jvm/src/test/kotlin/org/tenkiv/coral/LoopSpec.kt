package org.tenkiv.coral

import io.kotlintest.specs.StringSpec

class LoopSpec : StringSpec({

    "Loop should break when escape() is called" {
        var a = 0

        loop {
            a++
            if (a == 10) return@loop escape()

            Unit
        }

    }

})