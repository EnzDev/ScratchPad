package fr.xsystems.scratchpad.testing.spy

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test


class SavingSpyTest {
    @Test
    fun shouldSaveTheCallWhenDataIsPassed() {
        val spy = SavingSpy<String>()
        val data = "TestData"

        spy.trigger(data)

        assertThat(spy.called()).isTrue
        assertThat(spy.lastData).isEqualTo(data)
    }

    @Test
    fun shouldSaveLastInfoWhenCalledMultipleTime() {
        val spy = SavingSpy<String>()
        val data = "TestData"

        spy.trigger("X")
        spy.trigger(data)

        assertThat(spy.called()).isTrue
        assertThat(spy.lastData).isEqualTo(data)
    }
}
