/*
 * Copyright 2018 Tenkiv, Inc.
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
@file:JvmName("NumberCommon")

package org.tenkiv.coral

import kotlin.jvm.JvmName
import kotlin.math.abs
import kotlin.math.ulp

private const val DEFAULT_DOUBLE_ULPS = 2_000

fun Double.feq(comparate: Double, epsilon: Double): Boolean = abs(this - comparate) <= epsilon

fun Double.feq(comparate: Double, maxUlps: Int): Boolean {
    //TODO: This might be more efficient if the double could broken down to long bits.
    val epsilon = if (comparate > this) comparate.ulp * maxUlps else this.ulp * maxUlps

    return feq(comparate, epsilon)
}

infix fun Double.feq(comparate: Double): Boolean = feq(comparate, DEFAULT_DOUBLE_ULPS)

fun Float.feq(comparate: Float, epsilon: Float): Boolean = abs(this - comparate) <= epsilon
