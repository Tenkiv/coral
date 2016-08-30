package org.tenkiv.coral

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import kotlin.reflect.KProperty


class AlreadySetException : UnsupportedOperationException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

class KeyNotFoundException : Throwable {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

open class UniMutDelegate<T : Any> internal constructor(protected open var onSet: ((T) -> Unit)?,
                                                        private val onGet: (T?) -> Unit) {
    open var value: T? = null

    open operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        onGet(value)
        return value ?:
                throw UninitializedPropertyAccessException("Attempted to access unitmut property before it was set")
    }

    open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value == null) {
            this.value = value
            onSet!!(value)
            onSet = null
        } else
            throw AlreadySetException("Attempted to set a SetOnce property that was already set.")
    }
}

private class PublicationSafeUnitMutDelegate<T : Any>(onSet: (T) -> Unit,
                                                      onGet: (T?) -> Unit) : UniMutDelegate<T>(onSet, onGet) {
    @Volatile override var value: T? = null
    @Volatile override var onSet: ((T) -> Unit)? = onSet
}

private class SynchronisedUniMutDelegate<T : Any>(onSet: (T) -> Unit,
                                                  onGet: (T?) -> Unit) : UniMutDelegate<T>(onSet, onGet) {
    @Volatile override var onSet: ((T) -> Unit)? = onSet
    private val lock = ReentrantReadWriteLock()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = lock.read {
        super.getValue(thisRef, property)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        lock.write { super.setValue(thisRef, property, value) }
    }
}

/**
 * Creates a delegate for properties that you want to mutate / set exactly one time but may not have the necessary
 * information to do so at compile time.
 *
 * @param onSet is called after setting the backing field to the new value.
 * @param onGet is called before returning the value of the property.
 */
fun <T : Any> unimut(threadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.PUBLICATION,
                     onGet: (T?) -> Unit = {},
                     onSet: (T) -> Unit = {}): UniMutDelegate<T> {
    when (threadSafetyMode) {
        LazyThreadSafetyMode.SYNCHRONIZED -> return SynchronisedUniMutDelegate(onSet, onGet)
        LazyThreadSafetyMode.PUBLICATION -> return PublicationSafeUnitMutDelegate(onSet, onGet)
        LazyThreadSafetyMode.NONE -> return UniMutDelegate(onSet, onGet)
    }
}