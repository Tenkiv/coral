package org.tenkiv.coral

import java.time.Duration

val Long.SecondsSpan: Duration
    get() = Duration.ofSeconds(this)

val Long.HoursSpan: Duration
    get() = Duration.ofHours(this)

val Long.DaysSpan: Duration
    get() = Duration.ofDays(this)

val Long.MillisSpan: Duration
    get() = Duration.ofMillis(this)

val Long.MinutesSpan: Duration
    get() = Duration.ofMinutes(this)

val Long.NanosSpan: Duration
    get() = Duration.ofNanos(this)