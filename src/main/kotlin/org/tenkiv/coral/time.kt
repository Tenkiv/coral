package org.tenkiv.coral

import java.time.Instant
import java.time.temporal.TemporalAmount

fun Instant.isOlderThan(age: TemporalAmount, now: Instant = Instant.now()) =
        if ((now - age).isAfter(this)) true else false