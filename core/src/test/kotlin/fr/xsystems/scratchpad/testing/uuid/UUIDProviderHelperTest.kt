package fr.xsystems.scratchpad.testing.uuid

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class UUIDProviderHelperTest {
    @Test
    fun shouldAlwaysRepeatWithOneUID() {
        val uuid = UUID.randomUUID()
        val uuidProvider = UUIDProviderHelper(uuid)

        val first = uuidProvider.nextUUID()
        uuidProvider.nextUUID()
        uuidProvider.nextUUID()
        val last = uuidProvider.nextUUID()

        Assertions.assertThat(first).isEqualTo(uuid)
        Assertions.assertThat(last).isEqualTo(uuid)
    }

    @Test
    fun shouldNotFailWhenEndReached() {
        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        val uuid3 = UUID.randomUUID()
        val uuidProvider = UUIDProviderHelper(uuid1, uuid2, uuid3)

        uuidProvider.nextUUID()
        uuidProvider.nextUUID()
        uuidProvider.nextUUID()
        val lastValue = uuidProvider.nextUUID()

        Assertions.assertThat(lastValue).isNotNull
    }

    @Test
    fun shouldCycleThroughUID() {
        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        val uuid3 = UUID.randomUUID()
        val uuidProvider = UUIDProviderHelper(uuid1, uuid2, uuid3)

        val first = uuidProvider.nextUUID()
        val second = uuidProvider.nextUUID()
        val third = uuidProvider.nextUUID()
        val fourth = uuidProvider.nextUUID()

        Assertions.assertThat(first).isEqualTo(uuid1)
        Assertions.assertThat(second).isEqualTo(uuid2)
        Assertions.assertThat(third).isEqualTo(uuid3)
        Assertions.assertThat(fourth).isEqualTo(uuid1)
    }
}