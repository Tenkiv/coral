package org.tenkiv.coral

import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

//TODO: Change these to data classes when Kotlin 1.1 is released.
//TODO: Maybe change these to value types when Kotlin adds support for such a thing.

open class ReadWriteLockedVal<out T>(open val value: T,
                                     val lock: ReadWriteLock = ReentrantReadWriteLock()) {

    inline fun <R> read(action: (T) -> R): R = lock.readLock().withLock { action(value) }

    /**
     * Unlike Kotlin's built in write function for ReentrantReadWriteLocks, this method will not automatically upgrade
     * from a read lock to a write lock. This won't be a problem if you only use the methods
     * built into LockedVal (lockedVal.read/modify) but you have to be careful if you're manually using the lock
     * associated with this LockedVal.
     */
    inline fun <R> modify(action: (T) -> R): R = lock.writeLock().withLock { action(value) }

}

class ReadWriteLockedVar<T>(override var value: T,
                            lock: ReadWriteLock = ReentrantReadWriteLock()) : ReadWriteLockedVal<T>(value, lock) {

    fun safeSet(obj: T) {
        lock.writeLock().withLock { this.value = obj }
    }
}
