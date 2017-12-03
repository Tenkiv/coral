package org.tenkiv.coral

//TODO Add an orNull and orDefault variant of these functions.
inline fun <E> Collection<E>.average(getValue: (E) -> Double): Double {
    var totalValue = 0.0
    forEach {
        totalValue += getValue(it)
    }
    return totalValue / size
}

inline fun <E> Collection<E>.average(getValue: (E) -> Double, condition: (E) -> Boolean):
        Double {
    var totalValue = 0.0
    var totalElements = 0
    forEach {
        if (condition(it)) {
            totalValue += getValue(it)
            totalElements++
        }
    }
    return totalValue / totalElements
}

inline fun <E> Collection<E>.findRatio(of: (E) -> Double, to: (E) -> Double): Double {
    var firstTotal = 0.0
    var secondTotal = 0.0
    forEach {
        firstTotal += of(it)
        secondTotal += to(it)
    }
    return firstTotal / secondTotal
}

inline fun <E> Collection<E>.findBooleanRatio(of: (E) -> Boolean, to: (E) -> Boolean): Double {
    var firstTotal = 0
    var secondTotal = 0.0
    forEach {
        if (of(it)) firstTotal++
        if (to(it)) secondTotal++
    }
    return firstTotal / secondTotal
}

inline fun <E> Collection<E>.findRatioToTotal(of: (E) -> Double, other: (E) -> Double): Double {
    var firstTotal = 0.0
    var secondTotal = 0.0
    forEach {
        firstTotal += of(it)
        secondTotal += other(it)
    }
    return firstTotal / (firstTotal + secondTotal)
}

inline fun <E> Collection<E>.findBooleanRatioToTotal(of: (E) -> Boolean, other: (E) -> Boolean): Double {
    var firstTotal = 0
    var secondTotal = 0.0
    forEach {
        if (of(it)) firstTotal++
        if (other(it)) secondTotal++
    }
    return firstTotal / (firstTotal + secondTotal)
}