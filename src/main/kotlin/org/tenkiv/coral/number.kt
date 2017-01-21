package org.tenkiv.coral

private const val DEFAULT_MAX_ULPS = 10

infix fun Double.pow(exponent: Double) = Math.pow(this, exponent)

fun Double.feq(comparate: Double, epsilon: Double) = Math.abs(this - comparate) <= epsilon

infix fun Double.feq(comparate: Double): Boolean {
    //TODO: This could be more efficient if the double could broken down to long bits.
    val epsilon = if (comparate > this) Math.ulp(comparate) * DEFAULT_MAX_ULPS else Math.ulp(this) * DEFAULT_MAX_ULPS

    return feq(comparate, epsilon)
}

fun Float.feq(comparate: Float, epsilon: Float) = Math.abs(this - comparate) <= epsilon

infix fun Float.feq(comparate: Float): Boolean {
    //TODO: This could be more efficient if the double could broken down to int bits.
    val epsilon = if (comparate > this) Math.ulp(comparate) * DEFAULT_MAX_ULPS else Math.ulp(this) * DEFAULT_MAX_ULPS

    return feq(comparate, epsilon)
}