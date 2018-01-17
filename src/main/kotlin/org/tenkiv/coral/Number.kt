package org.tenkiv.coral

private const val DEFAULT_DOUBLE_ULPS = 2_000
private const val DEFAULT_FLOAT_ULPS = 200

fun Double.feq(comparate: Double, epsilon: Double): Boolean = Math.abs(this - comparate) <= epsilon

fun Double.feq(comparate: Double, maxUlps: Int): Boolean {
    //TODO: This might be more efficient if the double could broken down to long bits.
    val epsilon = if (comparate > this) Math.ulp(comparate) * maxUlps else Math.ulp(this) * maxUlps

    return feq(comparate, epsilon)
}

infix fun Double.feq(comparate: Double): Boolean = feq(comparate, DEFAULT_DOUBLE_ULPS)

fun Float.feq(comparate: Float, epsilon: Float): Boolean = Math.abs(this - comparate) <= epsilon

fun Float.feq(comparate: Float, maxUlps: Int): Boolean {
    //TODO: This might be more efficient if the double could broken down to long bits.
    val epsilon = if (comparate > this) Math.ulp(comparate) * maxUlps else Math.ulp(this) * maxUlps

    return feq(comparate, epsilon)
}

infix fun Float.feq(comparate: Float): Boolean = feq(comparate, DEFAULT_FLOAT_ULPS)