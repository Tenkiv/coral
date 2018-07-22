package org.tenkiv.coral

import kotlin.jvm.JvmField

inline fun loop(block: LoopControl.() -> Unit) {

    while (true)
        try {
            LoopControl.instance.block()
        } catch (control: LoopControl.Break) {
            break
        }

}

inline fun <T> Iterator<T>.forEachLoop(operation: LoopControl.(T) -> Unit) {
    for (element in this)
        try {
            LoopControl.instance.operation(element)
        } catch (control: LoopControl.Break) {
            break
        }
}

inline fun <T> Iterable<T>.forEachLoop(operation: LoopControl.(T) -> Unit) {
    for (element in this)
        try {
            LoopControl.instance.operation(element)
        } catch (control: LoopControl.Break) {
            break
        }
}

class LoopControl private constructor() {

    fun breakLoop(): Nothing = throw Break

    internal object Break : Throwable()

    companion object {
        @PublishedApi
        @JvmField
        internal val instance = LoopControl()
    }

}
