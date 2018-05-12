package org.tenkiv.coral

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
