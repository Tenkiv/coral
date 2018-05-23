package org.tenkiv.coral

inline fun loop(block: LoopUtils.() -> Any?) {

    while (LoopUtils.instance.block() != LoopControl.BREAK)
        continue

}

enum class LoopControl {
    CONTINUE,
    BREAK
}

class LoopUtils private constructor() {

    fun next() = LoopControl.CONTINUE

    fun escape() = LoopControl.BREAK

    @PublishedApi
    internal companion object {
        val instance = LoopUtils()
    }

}
