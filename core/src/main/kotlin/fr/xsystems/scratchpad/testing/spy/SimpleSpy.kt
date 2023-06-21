package fr.xsystems.scratchpad.testing.spy

import java.time.LocalDateTime

open class SimpleSpy {
    private val calls: MutableList<LocalDateTime> = mutableListOf()
    fun trigger() = let { calls += LocalDateTime.now() }

    fun called() = calls.isNotEmpty()
    fun notCalled() = calls.isEmpty()
    fun calledExactly(expected: Int) = calls.size == expected
    fun calledExactlyOnce() = calledExactly(1)
}

