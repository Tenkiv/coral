package org.tenkiv.coral

import java.time.Instant
import java.time.temporal.TemporalAmount

fun Instant.isOlderThan(age: TemporalAmount, now: Instant = Instant.now()) =
        if (this.isBefore(now - age)) true else false