package org.tenkiv.coral

import kotlin.reflect.KClass

/**
 * Checks to see if one Kotlin type conforms to another. Will return the same result as if the 'is' operator were
 * used to check an instance of the class against a type.
 */
infix fun <T : Any, C : Any> KClass<T>.can(comparate: KClass<C>) =
    comparate.java.isAssignableFrom(this.java)