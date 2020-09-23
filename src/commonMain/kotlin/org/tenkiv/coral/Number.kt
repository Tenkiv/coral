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
@file:Suppress("NOTHING_TO_INLINE")

package org.tenkiv.coral

import kotlin.jvm.*
import kotlin.math.*

//▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬ ஃ Type Aliases ஃ ▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
// TODO: Documentation of typealiased numbers

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

public inline fun Number.toInt8(): Int8 = toByte()
public inline fun Number.toInt16(): Int16 = toShort()
public inline fun Number.toInt32(): Int32 = toInt()
public inline fun Number.toInt64(): Int64 = toLong()
public inline fun Number.toFloat32(): Float32 = toFloat()
public inline fun Number.toFloat64(): Float64 = toDouble()

public inline fun UInt8.toUInt16(): UInt16 = toUShort()
public inline fun UInt8.toUInt32(): UInt32 = toUInt()
public inline fun UInt8.toUInt64(): UInt64 = toULong()
public inline fun UInt8.toInt8(): Int8 = toByte()
public inline fun UInt8.toInt16(): Int16 = toShort()
public inline fun UInt8.toInt32(): Int32 = toInt()
public inline fun UInt8.toInt64(): Int64 = toLong()
public inline fun UInt8.toFloat32(): Float32 = toFloat()
public inline fun UInt8.toFloat64(): Float64 = toDouble()

public inline fun UInt16.toUInt8(): UInt8 = toUByte()
public inline fun UInt16.toUInt32(): UInt32 = toUInt()
public inline fun UInt16.toUInt64(): UInt64 = toULong()
public inline fun UInt16.toInt8(): Int8 = toByte()
public inline fun UInt16.toInt16(): Int16 = toShort()
public inline fun UInt16.toInt32(): Int32 = toInt()
public inline fun UInt16.toInt64(): Int64 = toLong()
public inline fun UInt16.toFloat32(): Float32 = toFloat()
public inline fun UInt16.toFloat64(): Float64 = toDouble()

public inline fun UInt32.toUInt8(): UInt8 = toUByte()
public inline fun UInt32.toUInt16(): UInt16 = toUShort()
public inline fun UInt32.toUInt64(): UInt64 = toULong()
public inline fun UInt32.toInt8(): Int8 = toByte()
public inline fun UInt32.toInt16(): Int16 = toShort()
public inline fun UInt32.toInt32(): Int32 = toInt()
public inline fun UInt32.toInt64(): Int64 = toLong()
public inline fun UInt32.toFloat32(): Float32 = toFloat()
public inline fun UInt32.toFloat64(): Float64 = toDouble()

public inline fun UInt64.toUInt8(): UInt8 = toUByte()
public inline fun UInt64.toUInt16(): UInt16 = toUShort()
public inline fun UInt64.toUInt32(): UInt32 = toUInt()
public inline fun UInt64.toInt8(): Int8 = toByte()
public inline fun UInt64.toInt16(): Int16 = toShort()
public inline fun UInt64.toInt32(): Int32 = toInt()
public inline fun UInt64.toInt64(): Int64 = toLong()
public inline fun UInt64.toFloat32(): Float32 = toFloat()
public inline fun UInt64.toFloat64(): Float64 = toDouble()

public inline fun Int8.toUInt8(): UInt8 = toUByte()
public inline fun Int8.toUInt16(): UInt16 = toUShort()
public inline fun Int8.toUInt32(): UInt32 = toUInt()
public inline fun Int8.toUInt64(): UInt64 = toULong()
public inline fun Int8.toInt16(): Int16 = toShort()
public inline fun Int8.toInt32(): Int32 = toInt()
public inline fun Int8.toInt64(): Int64 = toLong()
public inline fun Int8.toFloat32(): Float32 = toFloat()
public inline fun Int8.toFloat64(): Float64 = toDouble()

public inline fun Int16.toUInt8(): UInt8 = toUByte()
public inline fun Int16.toUInt16(): UInt16 = toUShort()
public inline fun Int16.toUInt32(): UInt32 = toUInt()
public inline fun Int16.toUInt64(): UInt64 = toULong()
public inline fun Int16.toInt8(): Int8 = toByte()
public inline fun Int16.toInt32(): Int32 = toInt()
public inline fun Int16.toInt64(): Int64 = toLong()
public inline fun Int16.toFloat32(): Float32 = toFloat()
public inline fun Int16.toFloat64(): Float64 = toDouble()

public inline fun Int32.toUInt8(): UInt8 = toUByte()
public inline fun Int32.toUInt16(): UInt16 = toUShort()
public inline fun Int32.toUInt32(): UInt32 = toUInt()
public inline fun Int32.toUInt64(): UInt64 = toULong()
public inline fun Int32.toInt8(): Int8 = toByte()
public inline fun Int32.toInt16(): Int16 = toShort()
public inline fun Int32.toInt64(): Int64 = toLong()
public inline fun Int32.toFloat32(): Float32 = toFloat()
public inline fun Int32.toFloat64(): Float64 = toDouble()

public inline fun Int64.toUInt8(): UInt8 = toUByte()
public inline fun Int64.toUInt16(): UInt16 = toUShort()
public inline fun Int64.toUInt32(): UInt32 = toUInt()
public inline fun Int64.toUInt64(): UInt64 = toULong()
public inline fun Int64.toInt8(): Int8 = toByte()
public inline fun Int64.toInt16(): Int16 = toShort()
public inline fun Int64.toInt32(): Int32 = toInt()
public inline fun Int64.toFloat32(): Float32 = toFloat()
public inline fun Int64.toFloat64(): Float64 = toDouble()

public inline fun Float32.toUInt32(): UInt32 = toUInt()
public inline fun Float32.toUInt64(): UInt64 = toULong()
public inline fun Float32.toInt32(): Int32 = toInt()
public inline fun Float32.toInt64(): Int64 = toLong()
public inline fun Float32.toFloat64(): Float64 = toDouble()

public inline fun Float64.toUInt32(): UInt32 = toUInt()
public inline fun Float64.toUInt64(): UInt64 = toULong()
public inline fun Float64.toInt32(): Int32 = toInt()
public inline fun Float64.toInt64(): Int64 = toLong()
public inline fun Float64.toFloat32(): Float32 = toFloat()
public inline fun Float64.toFloat64(): Float64 = toDouble()


//▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬ ஃ feq ஃ ▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬

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

//▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬ ஃ Other Math ஃ ▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬

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