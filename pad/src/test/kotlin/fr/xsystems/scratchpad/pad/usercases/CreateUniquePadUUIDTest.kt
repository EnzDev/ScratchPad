package fr.xsystems.scratchpad.pad.usercases

import fr.xsystems.scratchpad.pad.gateway.PadRepositorySpy
import fr.xsystems.scratchpad.pad.gateway.UUIDProviderStub
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*


class CreateUniquePadUUIDTest {
    @Test
    fun shouldRetrieveAnUUIDFromGenerator() {
        val (uc) = createUCWithUUIDs()

        val padUUID = uc.execute(Unit)

        assertThat(padUUID).isNotNull
        assertThat(padUUID.uuid).isEqualTo(usableUUID)
    }

    @Test
    fun shouldCheckForExistence() {
        val (uc, padRepo) = createUCWithUUIDs()

        uc.execute(Unit)

        assertThat(padRepo.existSpy.calledExactlyOnce()).isTrue
        assertThat(padRepo.existSpy.lastData).isEqualTo(usableUUID)
    }

    @Test
    fun shouldNotUseExistingUUID() {
        val (uc, padRepo) = createUCWithUUIDs(existingUUID, usableUUID)

        val padUUID = uc.execute(Unit)

        assertThat(padRepo.existSpy.calledExactly(2)).isTrue
        assertThat(padUUID.uuid).isEqualTo(usableUUID)
    }

    companion object {
        val usableUUID = UUID.randomUUID()
        val existingUUID = UUID.randomUUID()

        private fun createUCWithUUIDs(vararg uids: UUID = arrayOf(usableUUID)): Pair<CreateUniquePadUUID, PadRepositorySpy> {
            val padRepo = PadRepositorySpy { it == existingUUID }
            val uuidProvider = UUIDProviderStub(*uids)
            return CreateUniquePadUUID(padRepo, uuidProvider) to padRepo
        }
    }
}