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


open class UniMutDelegate<T : Any>(private val onSet: (T) -> Unit, private val onGet: (T?) -> Unit) {
    var value: T? = null

    open operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        onGet(value)
        return value ?:
                throw UninitializedPropertyAccessException("Attempted to access Unitmut property before it was set")
    }

    open operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value == null) {
            this.value = value
            onSet(value)
        } else
            throw AlreadySetException("Attempted to set a SetOnce property that was already set.")
    }
}

private class SynchronisedUniMutDelegate<T : Any>(onSet: (T) -> Unit,
                                                  onGet: (T?) -> Unit) : UniMutDelegate<T>(onSet, onGet) {
    private val lock = ReentrantReadWriteLock()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = lock.read {
        super.getValue(thisRef, property)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        lock.write { super.setValue(thisRef, property, value) }
    }
}

enum class UniMutThreadSafetyMode {
    SYNCHRONIZED,
    NONE
}

/**
 * Creates a delegate for properties that you want to mutate / set exactly one time but may not have the necessary
 * information to do so at compile time.
 *
 * @param onSet is called after setting the backing field to the new value.
 * @param onGet is called before returning the value of the property.
 */
fun <T : Any> unimut(threadSafetyMode: UniMutThreadSafetyMode = UniMutThreadSafetyMode.NONE,
                     onGet: (T?) -> Unit = {},
                     onSet: (T) -> Unit = {}): UniMutDelegate<T> {
    when (threadSafetyMode) {
        UniMutThreadSafetyMode.SYNCHRONIZED -> return SynchronisedUniMutDelegate(onSet, onGet)
        UniMutThreadSafetyMode.NONE -> return UniMutDelegate(onSet, onGet)
    }
}