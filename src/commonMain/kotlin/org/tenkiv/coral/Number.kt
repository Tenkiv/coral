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
public typealias U8 = UByte
public typealias U16 = UShort
public typealias U32 = UInt
public typealias U64 = ULong

// signed integer types
public typealias I8 = Byte
public typealias I16 = Short
public typealias I32 = Int
public typealias I64 = Long

// floating point types
public typealias F32 = Float
public typealias F64 = Double

private const val DEFAULT_DOUBLE_ULPS = 2_000

public fun F64.feq(comparate: F64, epsilon: F64): Boolean = abs(this - comparate) <= epsilon

public fun F64.feq(comparate: F64, maxUlps: I32): Boolean {
    //TODO: This might be more efficient if the double could broken down to long bits.
    val epsilon = if (comparate > this) comparate.ulp * maxUlps else this.ulp * maxUlps

    return feq(comparate, epsilon)
}

public infix fun F64.feq(comparate: F64): Boolean = feq(comparate, DEFAULT_DOUBLE_ULPS)

public fun F32.feq(comparate: F32, epsilon: F32): Boolean = abs(this - comparate) <= epsilon


private const val EXCEPTION_MSG = "The number being normalised is not in the specified range"

/**
 * @return null if the number to be normalised is outside the range.
 */
public infix fun F64.normalToOrNull(range: ClosedRange<F64>): F64? {
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
public infix fun F64.normalTo(range: ClosedRange<F64>): F64 {
    val min = range.start
    val max = range.endInclusive

    return if (this in range) {
        normalise(this, min, max)
    } else {
        throw IllegalArgumentException(EXCEPTION_MSG)
    }
}

private fun normalise(number: F64, min: F64, max: F64): F64 = (number - min) / (max - min)

/**
 * @return null if the number to be normalised is outside the range.
 */
public infix fun F32.normalToOrNull(range: ClosedRange<F32>): F32? {
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
public infix fun F32.normalTo(range: ClosedRange<F32>): F32 {
    val min = range.start
    val max = range.endInclusive

    return if (this in range) {
        normalise(this, min, max)
    } else {
        throw IllegalArgumentException(EXCEPTION_MSG)
    }
}

private fun normalise(number: F32, min: F32, max: F32): F32 = (number - min) / (max - min)