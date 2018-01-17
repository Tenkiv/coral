package org.tenkiv.coral

import java.time.Duration
import java.time.Instant
import java.time.temporal.TemporalAmount

val Long.secondsSpan: Duration get() = Duration.ofSeconds(this)

val Long.hoursSpan: Duration get() = Duration.ofHours(this)

val Long.daysSpan: Duration get() = Duration.ofDays(this)

val Long.millisSpan: Duration get() = Duration.ofMillis(this)

val Long.minutesSpan: Duration get() = Duration.ofMinutes(this)

val Long.nanosSpan: Duration get() = Duration.ofNanos(this)

fun Instant.isOlderThan(age: TemporalAmount, now: Instant = Instant.now()) =
    this.isBefore(now - age)