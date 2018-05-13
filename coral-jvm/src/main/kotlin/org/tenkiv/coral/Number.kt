package org.tenkiv.coral

import kotlin.math.ulp

private const val DEFAULT_FLOAT_ULPS = 200

fun Float.feq(comparate: Float, maxUlps: Int): Boolean {
    //TODO: This might be more efficient if the float could broken down to long bits.
    val epsilon = if (comparate > this) comparate.ulp * maxUlps else this.ulp * maxUlps

    return feq(comparate, epsilon)
}

infix fun Float.feq(comparate: Float): Boolean = feq(comparate, DEFAULT_FLOAT_ULPS)