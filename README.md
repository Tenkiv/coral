# Coral
Some added niceties for Kotlin.

Current features:
* unimut() delegate that initialises a variable to null then allows to be set exactly once at any time.
Has optional Thread synchronisation built in, as well as optional onSet() and onGet() functions to be executed
accordingly. It throws a specific exception if you attempt to set it more than once which means you don't need to check
for null when using a variable set with this delegate, works similarly to lateinit var.
* Locked and LockedVar are very simple constructs for pairing a lock with an object, currently it's meant exclusively for
use with ReentrantReadWriteLocks.

Coral is in a very early experimental stage of development.
