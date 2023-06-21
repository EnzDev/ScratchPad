package fr.xsystems.scratchpad.pad.usercases

import fr.xsystems.scratchpad.administration.entities.UserUUID
import fr.xsystems.scratchpad.pad.entities.Pad
import fr.xsystems.scratchpad.pad.entities.PadMode
import fr.xsystems.scratchpad.pad.entities.PadUUID
import fr.xsystems.scratchpad.pad.gateway.DateTimeProviderStub
import fr.xsystems.scratchpad.pad.gateway.PadRepositorySpy
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.from
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class CreateANewPadTest {
    val expected = Pad(
        PadUUID(UUID.randomUUID()),
        "My pad",
        PadMode.INSTANT_MESSENGER,
        UserUUID(UUID.randomUUID()),
        ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault())
    )

    @Test
    fun shouldSaveTheNewPadWithTheCorrectInfo() {
        val padRepo = PadRepositorySpy()
        val dtProvider = DateTimeProviderStub(expected.creation!!)
        val uc = CreateANewPad(padRepo, dtProvider)
        val req = CreateANewPad.PadCreation(expected.id.uuid, expected.name, expected.owner)

        uc.execute(req)

        assertThat(padRepo.saveSpy.calledExactlyOnce())
            .isTrue
        val savedPad = padRepo.saveSpy.lastData
        assertThat(savedPad).isNotNull
        assertThat(savedPad!!)
            .returns(expected.name, from { it.name })
            .returns(expected.mode, from { it.mode })
            .returns(expected.creation, from { it.creation })
            .returns(expected.id.uuid, from { it.id.uuid })
            .returns(expected.owner.uuid, from { it.owner.uuid })
    }

    @Test
    fun shouldGiveTheNewPadUUID() {
        val padRepo = PadRepositorySpy()
        val dtProvider = DateTimeProviderStub(expected.creation!!)
        val uc = CreateANewPad(padRepo, dtProvider)
        val req = CreateANewPad.PadCreation(expected.id.uuid, expected.name, expected.owner)

        val pad = uc.execute(req)

        assertThat(pad.id).isEqualTo(expected.id)
    }

    @Test
    fun shouldThrowIfUUIDIsDuplicated() {
        val padRepo = PadRepositorySpy { true }
        val dtProvider = DateTimeProviderStub(expected.creation!!)
        val uc = CreateANewPad(padRepo, dtProvider)
        val req = CreateANewPad.PadCreation(expected.id.uuid, expected.name, expected.owner)

        assertThrows<CreateANewPad.DuplicatedPadUUIDException> {
            uc.execute(req)
        }

        assertThat(padRepo.existSpy.calledExactlyOnce())
            .isTrue
        assertThat(padRepo.saveSpy.notCalled())
            .isTrue
    }

    @Test
    fun shouldSaveCorrectlyWithAllTheMode() {
        PadMode.values().forEach { expectedMode ->
            val padRepo = PadRepositorySpy()
            val dtProvider = DateTimeProviderStub(expected.creation!!)
            val uc = CreateANewPad(padRepo, dtProvider)
            val req = CreateANewPad.PadCreation(expected.id.uuid, expected.name, expected.owner, expectedMode)

            uc.execute(req)

            val savedPad = padRepo.saveSpy.lastData
            assertThat(savedPad?.mode).isEqualTo(expectedMode)
        }
    }
}