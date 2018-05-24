package org.tenkiv.coral

inline fun loop(block: LoopControl.() -> Unit) {

    while (true)
        try {
            LoopControl.instance.block()
        } catch (control: LoopControl.Break) {
            break
        }

}

class LoopControl private constructor() {

    fun escape(): Nothing = throw Break

    internal object Break : Throwable()

    companion object {
        @PublishedApi
        internal val instance = LoopControl()
    }

}
