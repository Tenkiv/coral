package org.tenkiv.coral

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

//TODO: Change these to data classes when Kotlin 1.1 is released.
//TODO: Probably change these to value types when Kotlin adds support for such a thing.

open class LockedVal<out T>(open val value: T,
                            open val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()) {

    inline fun <R> read(action: (T) -> R): R = lock.read { action(value) }

    inline fun <R> modify(action: (T) -> R): R = lock.write { action(value) }

}

open class LockedVar<T>(override var value: T,
                        lock: ReentrantReadWriteLock = ReentrantReadWriteLock()) : LockedVal<T>(value, lock) {

    open fun safeSet(obj: T) {
        lock.write { this.value = obj }
    }
}
