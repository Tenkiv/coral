# Coral &nbsp;[![License](http://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT) [![Matrix](https://img.shields.io/badge/chat-matrix-green.svg)](https://matrix.to/#/!zNBZBfPOrFpuWSwlFw:matrix.org?via=matrix.org)
Coral is a common set of kotlin stdlib and platform stdlib extensions used in many of our Kotlin projects at Tenkiv.

 | | |
 :-----: | :-----:
 JVM | placeholder

Current features:
* `unimut()` delegate that initialises a variable to null then allows to be set exactly once at any time.
Has optional Thread synchronisation built in, as well as optional `onSet()` and `onGet()` functions to be executed
accordingly. It throws a specific exception if you attempt to set it more than once which means you don't need to check
for null when using a variable set with this delegate, works similarly to lateinit var.
* `feq()` operator for generalised floating point equality.
* Long extension properties for converting kotlin Longs into Durations.
* `can()` operator for checking if a class is equal to or inherits from another class (like the is operator but for classes instead of instances of classes, in Java this is the `isAssignableFrom()` method).
* `ValueInstant` typed tuple for pairing any value with a moment in time.
* `average()` and `findRatio()` extension functions for collections.
