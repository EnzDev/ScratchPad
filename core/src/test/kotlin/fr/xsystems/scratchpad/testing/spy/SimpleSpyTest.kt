package fr.xsystems.scratchpad.testing.spy

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test


class SimpleSpyTest {
    @Test
    fun shouldHaveInfoWhenCallOnce() {
        val spy = SimpleSpy()

        spy.trigger()

        assertThat(spy.called()).isTrue
        assertThat(spy.calledExactlyOnce()).isTrue
        assertThat(spy.calledExactly(1)).isTrue
        assertThat(spy.notCalled()).isFalse
    }

    @Test
    fun shouldHaveInfoWhenNoCall() {
        val spy = SimpleSpy()

        // No op

        assertThat(spy.called()).isFalse
        assertThat(spy.calledExactlyOnce()).isFalse
        assertThat(spy.calledExactly(0)).isTrue
        assertThat(spy.notCalled()).isTrue
    }

    @Test
    fun shouldHaveInfoWhenMultipleCall() {
        val spy = SimpleSpy()

        spy.trigger()
        spy.trigger()
        spy.trigger()

        assertThat(spy.called()).isTrue
        assertThat(spy.calledExactlyOnce()).isFalse
        assertThat(spy.calledExactly(2)).isFalse
        assertThat(spy.calledExactly(3)).isTrue
        assertThat(spy.calledExactly(4)).isFalse
        assertThat(spy.notCalled()).isFalse
    }
}
