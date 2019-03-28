/*
 * Copyright 2019 Tenkiv, Inc.
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

package org.tenkiv.coral

//TODO Add an orNull and orDefault variant of these functions.
public inline fun <E> Iterable<E>.average(getValue: (E) -> Double): Double {
    var totalValue = 0.0
    var count = 0
    forEach {
        totalValue += getValue(it)
        count++
    }
    return if (count == 0) Double.NaN else totalValue / count
}

public inline fun <E> Iterable<E>.average(getValue: (E) -> Double, condition: (E) -> Boolean): Double {
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
public inline fun <E> Iterable<E>.ratio(of: (E) -> Double, to: (E) -> Double): Double {
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
public inline fun <E> Iterable<E>.booleanRatio(of: (E) -> Boolean, to: (E) -> Boolean): Double {
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

public inline fun <E> Collection<E>.ratioToTotal(of: (E) -> Double, other: (E) -> Double): Double {
    var ofTotal = 0.0
    var otherTotal = 0.0
    forEach {
        ofTotal += of(it)
        otherTotal += other(it)
    }
    val total = ofTotal + otherTotal
    return if (total == 0.0) Double.NaN else ofTotal / total
}

public inline fun <E> Collection<E>.booleanRatioToTotal(of: (E) -> Boolean, other: (E) -> Boolean): Double {
    var ofTotal = 0
    var otherTotal = 0.0
    forEach {
        if (of(it)) ofTotal++
        if (other(it)) otherTotal++
    }
    val total = ofTotal + otherTotal
    return if (total == 0.0) Double.NaN else ofTotal / total
}