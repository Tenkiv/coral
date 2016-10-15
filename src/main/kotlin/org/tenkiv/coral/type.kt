package org.tenkiv.coral

import java.time.Instant

interface ValueInstant<out T : Any> {
    val value: T
    val instant: Instant

    companion object {
        fun <T : Any> of(value: T, instant: Instant = Instant.now()): ValueInstant<T> =
                BasicValueInstant(value, instant)
    }
}

private data class BasicValueInstant<out T : Any>(override val value: T,
                                                  override val instant: Instant = Instant.now()) :
        ValueInstant<T>

//TODO: Add a data class that implements both ValueInstant and MeasurementCQ
