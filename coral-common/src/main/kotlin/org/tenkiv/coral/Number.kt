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


/**
 * @return null if the number to be normalised is outside the range.
 */
infix fun Double.normalToOrNull(range: ClosedRange<Double>): Double? =
    if (this in range) {
        normalTo(range)
    } else {
        null
    }

/**
 * Normalises the number to the given range.
 */
infix fun Double.normalTo(range: ClosedRange<Double>): Double {
    val min = range.start
    val max = range.endInclusive

    return normalise(this, min, max)
}

private fun normalise(number: Double, min: Double, max: Double): Double = (number - min) / (max - min)

/**
 * @return null if the number to be normalised is outside the range.
 */
infix fun Float.normalToOrNull(range: ClosedRange<Float>): Float? =
    if (this in range) {
        normalTo(range)
    } else {
        null
    }

/**
 * Normalises the number to the given range.
 */
infix fun Float.normalTo(range: ClosedRange<Float>): Float {
    val min = range.start
    val max = range.endInclusive

    return normalise(this, min, max)
}

private fun normalise(number: Float, min: Float, max: Float): Float = (number - min) / (max - min)