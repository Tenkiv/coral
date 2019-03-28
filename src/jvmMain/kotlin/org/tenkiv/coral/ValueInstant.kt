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
 *
 */

package org.tenkiv.coral

import java.time.*


public infix fun <T> T.at(instant: Instant): ValueInstant<T> =
    BasicValueInstant(this, instant)

public fun <T> T.now(): ValueInstant<T> = BasicValueInstant(this, Instant.now())

public fun <T> Iterable<ValueInstant<T>>.getDataInRange(instantRange: ClosedRange<Instant>): List<ValueInstant<T>> =
    this.filter { it.instant in instantRange }

public interface ValueInstant<out T> {
    public val value: T
    public val instant: Instant

    public operator fun component1() = value
    public operator fun component2() = instant

    public companion object {
        @Deprecated("Use at(Instant) function instead", ReplaceWith("at(Instant)"))
        public operator fun <T> invoke(value: T, instant: Instant = Instant.now()): ValueInstant<T> =
            BasicValueInstant(value, instant)
    }
}

private data class BasicValueInstant<out T>(
    override val value: T,
    override val instant: Instant = Instant.now()
) : ValueInstant<T>
