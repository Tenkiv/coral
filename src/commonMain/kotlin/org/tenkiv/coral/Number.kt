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

@file:JvmName("NumberCommon")

package org.tenkiv.coral

import kotlin.jvm.*
import kotlin.math.*

// unsigned integer types
public typealias UInt8 = UByte
public typealias UInt16 = UShort
public typealias UInt32 = UInt
public typealias UInt64 = ULong

// signed integer types
public typealias Int8 = Byte
public typealias Int16 = Short
public typealias Int32 = Int
public typealias Int64 = Long

// floating point types
public typealias Float32 = Float
public typealias Float64 = Double

private const val DEFAULT_DOUBLE_ULPS = 2_000

public fun Float64.feq(comparate: Float64, epsilon: Float64): Boolean = abs(this - comparate) <= epsilon

public fun Float64.feq(comparate: Float64, maxUlps: Int32): Boolean {
    //TODO: This might be more efficient if the double could broken down to long bits.
    val epsilon = if (comparate > this) comparate.ulp * maxUlps else this.ulp * maxUlps

    return feq(comparate, epsilon)
}

public infix fun Float64.feq(comparate: Float64): Boolean = feq(comparate, DEFAULT_DOUBLE_ULPS)

public fun Float32.feq(comparate: Float32, epsilon: Float32): Boolean = abs(this - comparate) <= epsilon


private const val EXCEPTION_MSG = "The number being normalised is not in the specified range"

/**
 * @return null if the number to be normalised is outside the range.
 */
public infix fun Float64.normalToOrNull(range: ClosedRange<Float64>): Float64? {
    val min = range.start
    val max = range.endInclusive

    return if (this in range) {
        normalise(this, min, max)
    } else {
        null
    }
}

/**
 * @throws [IllegalArgumentException] if the number is not in the given range.
 *
 * Normalises the number to the given range.
 */
public infix fun Float64.normalTo(range: ClosedRange<Float64>): Float64 {
    val min = range.start
    val max = range.endInclusive

    return if (this in range) {
        normalise(this, min, max)
    } else {
        throw IllegalArgumentException(EXCEPTION_MSG)
    }
}

private fun normalise(number: Float64, min: Float64, max: Float64): Float64 = (number - min) / (max - min)

/**
 * @return null if the number to be normalised is outside the range.
 */
public infix fun Float32.normalToOrNull(range: ClosedRange<Float32>): Float32? {
    val min = range.start
    val max = range.endInclusive

    return if (this in range) {
        normalise(this, min, max)
    } else {
        null
    }
}

/**
 * @throws [IllegalArgumentException] if the number is not in the given range.
 *
 * Normalises the number to the given range.
 */
public infix fun Float32.normalTo(range: ClosedRange<Float32>): Float32 {
    val min = range.start
    val max = range.endInclusive

    return if (this in range) {
        normalise(this, min, max)
    } else {
        throw IllegalArgumentException(EXCEPTION_MSG)
    }
}

private fun normalise(number: Float32, min: Float32, max: Float32): Float32 = (number - min) / (max - min)