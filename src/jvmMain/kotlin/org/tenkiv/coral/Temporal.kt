/*
 * Copyright 2020 Tenkiv, Inc.
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

import java.time.*
import java.time.temporal.*

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.seconds.toJavaDuration()", "kotlin.time.seconds")
)
public val Long.secondsSpan: Duration get() = Duration.ofSeconds(this)

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.seconds.toJavaDuration()", "kotlin.time.seconds")
)
public val Int.secondsSpan: Duration get() = Duration.ofSeconds(this.toLong())

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.hours.toJavaDuration()", "kotlin.time.seconds")
)
public val Long.hoursSpan: Duration get() = Duration.ofHours(this)

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.hours.toJavaDuration()", "kotlin.time.seconds")
)
public val Int.hoursSpan: Duration get() = Duration.ofHours(this.toLong())

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.days.toJavaDuration()", "kotlin.time.seconds")
)
public val Long.daysSpan: Duration get() = Duration.ofDays(this)

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.days.toJavaDuration()", "kotlin.time.seconds")
)
public val Int.daysSpan: Duration get() = Duration.ofDays(this.toLong())

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.milliseconds.toJavaDuration()", "kotlin.time.seconds")
)
public val Long.millisSpan: Duration get() = Duration.ofMillis(this)

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.milliseconds.toJavaDuration()", "kotlin.time.seconds")
)
public val Int.millisSpan: Duration get() = Duration.ofMillis(this.toLong())

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.minutes.toJavaDuration()", "kotlin.time.seconds")
)
public val Long.minutesSpan: Duration get() = Duration.ofMinutes(this)

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.minutes.toJavaDuration()", "kotlin.time.seconds")
)
public val Int.minutesSpan: Duration get() = Duration.ofMinutes(this.toLong())

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.nanoseconds.toJavaDuration()", "kotlin.time.seconds")
)
public val Long.nanosSpan: Duration get() = Duration.ofNanos(this)

@Deprecated(
    message = "Use Kotlin Duration instead.",
    replaceWith = ReplaceWith("this.nanoseconds.toJavaDuration()", "kotlin.time.seconds")
)
public val Int.nanosSpan: Duration get() = Duration.ofNanos(this.toLong())

public infix fun Instant.isOlderThan(age: TemporalAmount): Boolean =
    this.isBefore(Instant.now() - age)

public fun Instant.isOlderThan(age: TemporalAmount, now: Instant): Boolean =
    this.isBefore(now - age)