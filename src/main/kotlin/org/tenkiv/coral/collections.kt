package org.tenkiv.coral

import kotlin.concurrent.withLock


//TODO: Add one of these that averages Quantities instead of Doubles.
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

inline fun <E> SynchronisedCollection<E>.average(getValue: (E) -> Double) =
        lock.readLock().withLock { unSyncedCollection.average(getValue) }

inline fun <E> SynchronisedCollection<E>.average(getValue: (E) -> Double, condition: (E) -> Boolean) =
        lock.readLock().withLock { unSyncedCollection.average(getValue, condition) }

inline fun <E> SynchronisedCollection<E>.findRatio(of: (E) -> Double, to: (E) -> Double) =
        lock.readLock().withLock { unSyncedCollection.findRatio(of, to) }

inline fun <E> SynchronisedCollection<E>.findBooleanRatio(of: (E) -> Double, to: (E) -> Double) =
        lock.readLock().withLock { unSyncedCollection.findRatio(of, to) }
