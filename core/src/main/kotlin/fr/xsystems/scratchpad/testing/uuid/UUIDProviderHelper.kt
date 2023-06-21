package fr.xsystems.scratchpad.testing.uuid

import java.util.*

open class UUIDProviderHelper(vararg val uid: UUID) {
    private var index = 0
    fun nextUUID(): UUID = this.uid[nextIndex()]
    private fun nextIndex() = index.also {
        this.index = (it + 1) % uid.size
    }
}