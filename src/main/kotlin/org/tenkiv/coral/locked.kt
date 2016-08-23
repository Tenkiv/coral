package org.tenkiv.coral

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

interface Locked<out T> {
    val lock: ReentrantReadWriteLock
    val value: T

    fun <R> read(action: (T) -> R): R = lock.read { action(value) }

    fun <R> modify(action: (T) -> R): R = lock.write { action(value) }

}

interface LockedVar<T> : Locked<T> {
    override var value: T

    fun safeSet(obj: T) {
        lock.write { this.value = obj }
    }
}

data class LockedVal<out T>(override val value: T,
                            override val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()) : Locked<T>

data class LockedVarC<T>(override var value: T,
                         override val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()) : LockedVar<T>
