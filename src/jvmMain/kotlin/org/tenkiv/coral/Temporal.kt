/*
 * Copyright 2019 Tenkiv, Inc.
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

infix fun Instant.isOlderThan(age: TemporalAmount) =
    this.isBefore(Instant.now() - age)

fun Instant.isOlderThan(age: TemporalAmount, now: Instant) =
    this.isBefore(now - age)