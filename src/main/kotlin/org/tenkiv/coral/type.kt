package org.tenkiv.coral

import java.time.Instant

fun <T> T.at(instant: Instant = Instant.now()): ValueInstant<T> = BasicValueInstant(this, instant)

interface ValueInstant<out T> {
    val value: T
    val instant: Instant

    companion object {
        fun <T> of(value: T, instant: Instant = Instant.now()): ValueInstant<T> =
                BasicValueInstant(value, instant)
    }
}

private data class BasicValueInstant<out T>(override val value: T,
                                            override val instant: Instant = Instant.now()) :
        ValueInstant<T>

//TODO: Add a data class that implements both ValueInstant and MeasurementCQ
