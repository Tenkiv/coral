package org.tenkiv.coral

import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock


private const val ERROR_MSG_COMP = "Cannot guarantee thread safety of iterator accessed in this way."
private const val ERROR_MSG_RUN = "Cannot guarantee thread safety of iterator accessed in this way, " +
        "please use the forEachSync() method or the withIterator() method."

inline fun <T> SynchronisedCollection<T>.forEachSync(action: (T) -> Unit) {
    lock.readLock().withLock {
        unSyncedCollection.forEach { action(it) }
    }
}

/**
 * To maintain thread safety this iterator must not be accessed outside of the function it's passed into.
 * If you're going to access the list the iterator comes from from inside this method,
 * access the backing collection itself, everything in the action block is already synchronised.
 */
inline fun <T> SynchronisedCollection<T>.withIterator(action: (iterator: MutableIterator<T>) -> Unit) =
        lock.writeLock().withLock { action(unSyncedCollection.iterator()) }

//TODO: Change these to data classes.
open class SynchronisedVal<out T>(open val unSyncedValue: T,
                                  val lock: ReadWriteLock = ReentrantReadWriteLock()) {

    inline fun <R> read(action: (T) -> R): R = lock.readLock().withLock { action(unSyncedValue) }

    /**
     * Unlike Kotlin's built in write function for ReentrantReadWriteLocks, this method will not automatically upgrade
     * from a read lock to a write lock. This won't be a problem if you only use the methods
     * built into LockedVal (lockedVal.read/modify) but you have to be careful if you're manually using the lock
     * associated with this LockedVal.
     */
    inline fun <R> modify(action: (T) -> R): R = lock.writeLock().withLock { action(unSyncedValue) }

}

class SynchronisedVar<T>(override var unSyncedValue: T,
                         lock: ReadWriteLock = ReentrantReadWriteLock()) : SynchronisedVal<T>(unSyncedValue, lock) {

    fun syncSet(obj: T) {
        lock.writeLock().withLock { this.unSyncedValue = obj }
    }
}

class UnSynchronisedMethodException(message: String? = null,
                                    cause: Throwable? = null) : Throwable(message, cause)

//TODO: Add synchronised versions of built in Kotlin extensions.
interface SynchronisedCollection<E> : MutableCollection<E> {
    val lock: ReadWriteLock
    val unSyncedCollection: MutableCollection<E>

    //TODO: probably make these protected.
    val readLock: Lock
        get() = lock.readLock()
    val writeLock: Lock
        get() = lock.writeLock()

}

class SynchronisedSet<E>(override val lock: ReadWriteLock = ReentrantReadWriteLock(),
                         override val unSyncedCollection: MutableSet<E> = HashSet<E>()) :
        MutableSet<E>, SynchronisedCollection<E> {

    override val size: Int
        get() = readLock.withLock { unSyncedCollection.size }

    override fun contains(element: E) = readLock.withLock { unSyncedCollection.contains(element) }

    override fun containsAll(elements: Collection<E>) = readLock.withLock { unSyncedCollection.containsAll(elements) }

    override fun isEmpty() = readLock.withLock { unSyncedCollection.isEmpty() }

    override fun add(element: E) = writeLock.withLock { unSyncedCollection.add(element) }

    override fun addAll(elements: Collection<E>) = writeLock.withLock { unSyncedCollection.addAll(elements) }

    override fun clear() = writeLock.withLock { unSyncedCollection.clear() }

    override fun remove(element: E) = writeLock.withLock { unSyncedCollection.remove(element) }

    override fun removeAll(elements: Collection<E>) = writeLock.withLock { unSyncedCollection.removeAll(elements) }

    override fun retainAll(elements: Collection<E>) = writeLock.withLock { unSyncedCollection.retainAll(elements) }

    @Deprecated(ERROR_MSG_COMP, ReplaceWith("forEachSync()"), DeprecationLevel.HIDDEN)
    override fun iterator() = throw UnSynchronisedMethodException(ERROR_MSG_RUN)
}

class SynchronisedList<E>(override val lock: ReadWriteLock = ReentrantReadWriteLock(),
                          override val unSyncedCollection: MutableList<E> = ArrayList<E>()) :
        MutableList<E>, SynchronisedCollection<E> {

    override val size: Int
        get() = readLock.withLock { unSyncedCollection.size }

    /**
     * To maintain thread safety this iterator must not be accessed outside of the function it's passed into.
     * If you're going to access the list the iterator comes from from inside this method,
     * access the backing collection itself, everything in the action block is already synchronised.
     */
    inline fun withIterator(action: (iterator: MutableListIterator<E>) -> Unit, iteratorIndex: Int = 0) =
            writeLock.withLock { action(unSyncedCollection.listIterator(iteratorIndex)) }

    override operator fun get(index: Int) = readLock.withLock { unSyncedCollection[index] }

    override fun contains(element: E) = readLock.withLock { unSyncedCollection.contains(element) }

    override fun containsAll(elements: Collection<E>) = readLock.withLock { unSyncedCollection.containsAll(elements) }

    override fun indexOf(element: E) = readLock.withLock { unSyncedCollection.indexOf(element) }

    override fun isEmpty() = readLock.withLock { unSyncedCollection.isEmpty() }

    override fun lastIndexOf(element: E) = readLock.withLock { unSyncedCollection.lastIndexOf(element) }

    override fun add(element: E) = writeLock.withLock { unSyncedCollection.add(element) }

    override fun add(index: Int, element: E) = writeLock.withLock { unSyncedCollection.add(index, element) }

    override fun addAll(index: Int, elements: Collection<E>) =
            writeLock.withLock { unSyncedCollection.addAll(index, elements) }

    override fun addAll(elements: Collection<E>) = writeLock.withLock { unSyncedCollection.addAll(elements) }

    override fun clear() = writeLock.withLock { unSyncedCollection.clear() }

    override fun remove(element: E) = writeLock.withLock { unSyncedCollection.remove(element) }

    override fun removeAll(elements: Collection<E>) = writeLock.withLock { unSyncedCollection.removeAll(elements) }

    override fun removeAt(index: Int) = writeLock.withLock { unSyncedCollection.removeAt(index) }

    override fun retainAll(elements: Collection<E>) = writeLock.withLock { unSyncedCollection.retainAll(elements) }

    override fun set(index: Int, element: E) = writeLock.withLock { unSyncedCollection.set(index, element) }

    override fun subList(fromIndex: Int, toIndex: Int) =
            readLock.withLock { SynchronisedList(lock, unSyncedCollection.subList(fromIndex, toIndex)) }

    @Deprecated(ERROR_MSG_COMP, ReplaceWith("forEachSync()"), DeprecationLevel.ERROR)
    override fun iterator() = throw UnSynchronisedMethodException(ERROR_MSG_RUN)

    @Deprecated(ERROR_MSG_COMP, ReplaceWith("forEachSync()"), DeprecationLevel.ERROR)
    override fun listIterator() = throw UnSynchronisedMethodException(ERROR_MSG_RUN)

    @Deprecated(ERROR_MSG_COMP, ReplaceWith("withIterator()"), DeprecationLevel.ERROR)
    override fun listIterator(index: Int) = throw UnSynchronisedMethodException(ERROR_MSG_RUN)

}
