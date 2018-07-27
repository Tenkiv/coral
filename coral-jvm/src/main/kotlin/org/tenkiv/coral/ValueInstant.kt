package org.tenkiv.coral

import java.time.Instant


infix fun <T> T.at(instant: Instant): ValueInstant<T> = BasicValueInstant(this, instant)

fun <T> T.now(): ValueInstant<T> = BasicValueInstant(this, Instant.now())

fun <T> Iterable<ValueInstant<T>>.getDataInRange(instantRange: ClosedRange<Instant>): List<ValueInstant<T>> =
    this.filter { it.instant in instantRange }

interface ValueInstant<out T> {
    val value: T
    val instant: Instant

    operator fun component1() = value
    operator fun component2() = instant

    companion object {
        @Deprecated("Use at(Instant) function instead", ReplaceWith("at(Instant)"))
        operator fun <T> invoke(value: T, instant: Instant = Instant.now()): ValueInstant<T> =
            BasicValueInstant(value, instant)
    }
}

private data class BasicValueInstant<out T>(
    override val value: T,
    override val instant: Instant = Instant.now()
) : ValueInstant<T>
