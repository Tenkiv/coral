package org.tenkiv.coral

import java.time.Duration
import java.time.Instant
import java.time.temporal.TemporalAmount

val Long.secondsSpan: Duration get() = Duration.ofSeconds(this)

val Int.secondsSpan: Duration get() = Duration.ofSeconds(this.toLong())

val Long.hoursSpan: Duration get() = Duration.ofHours(this)

val Int.hoursSpan: Duration get() = Duration.ofHours(this.toLong())

val Long.daysSpan: Duration get() = Duration.ofDays(this)

val Int.daysSpan: Duration get() = Duration.ofDays(this.toLong())

val Long.millisSpan: Duration get() = Duration.ofMillis(this)

val Int.millisSpan: Duration get() = Duration.ofMillis(this.toLong())

val Long.minutesSpan: Duration get() = Duration.ofMinutes(this)

val Int.minutesSpan: Duration get() = Duration.ofMinutes(this.toLong())

val Long.nanosSpan: Duration get() = Duration.ofNanos(this)

val Int.nanosSpan: Duration get() = Duration.ofNanos(this.toLong())

fun Instant.isOlderThan(age: TemporalAmount, now: Instant = Instant.now()) =
    this.isBefore(now - age)