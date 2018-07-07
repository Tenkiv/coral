package org.tenkiv.coral

//TODO Add an orNull and orDefault variant of these functions.
inline fun <E> Iterable<E>.average(getValue: (E) -> Double): Double {
    var totalValue = 0.0
    var count = 0
    forEach {
        totalValue += getValue(it)
        count++
    }
    return if (count == 0) Double.NaN else totalValue / count
}

inline fun <E> Iterable<E>.average(getValue: (E) -> Double, condition: (E) -> Boolean): Double {
    var totalValue = 0.0
    var count = 0
    forEach {
        if (condition(it)) {
            totalValue += getValue(it)
            count++
        }
    }
    return if (count == 0) Double.NaN else totalValue / count
}

/**
 * Result always given to 1. So, for example, 3 to 1 would return 3 and 1.5 to 0.5 would also return 3.
 */
inline fun <E> Iterable<E>.ratio(of: (E) -> Double, to: (E) -> Double): Double {
    var ofTotal = 0.0
    var toTotal = 0.0
    forEach {
        ofTotal += of(it)
        toTotal += to(it)
    }
    return when {
        ofTotal == 0.0 && toTotal == 0.0 -> Double.NaN
        ofTotal <= 0.0 && toTotal >= 0.0 -> Double.NEGATIVE_INFINITY
        ofTotal >= 0.0 && toTotal <= 0.0 -> Double.POSITIVE_INFINITY
        else -> ofTotal / toTotal
    }
}

/**
 * Result always given to 1. So, for example, 3 to 1 would return 3 and 1.5 to 0.5 would also return 3.
 */
inline fun <E> Iterable<E>.booleanRatio(of: (E) -> Boolean, to: (E) -> Boolean): Double {
    var ofTotal = 0
    var toTotal = 0
    forEach {
        if (of(it)) ofTotal++
        if (to(it)) toTotal++
    }
    return when {
        ofTotal == 0 && toTotal == 0 -> Double.NaN
        ofTotal == 0 && toTotal > 0 -> Double.NEGATIVE_INFINITY
        ofTotal > 0 && toTotal == 0 -> Double.POSITIVE_INFINITY
        else -> ofTotal / toTotal.toDouble()
    }
}

inline fun <E> Collection<E>.ratioToTotal(of: (E) -> Double, other: (E) -> Double): Double {
    var ofTotal = 0.0
    var otherTotal = 0.0
    forEach {
        ofTotal += of(it)
        otherTotal += other(it)
    }
    val total = ofTotal + otherTotal
    return if (total == 0.0) Double.NaN else ofTotal / total
}

inline fun <E> Collection<E>.booleanRatioToTotal(of: (E) -> Boolean, other: (E) -> Boolean): Double {
    var ofTotal = 0
    var otherTotal = 0.0
    forEach {
        if (of(it)) ofTotal++
        if (other(it)) otherTotal++
    }
    val total = ofTotal + otherTotal
    return if (total == 0.0) Double.NaN else ofTotal / total
}