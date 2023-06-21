package fr.xsystems.scratchpad.pad.usercases

import fr.xsystems.scratchpad.administration.entities.UserUUID
import fr.xsystems.scratchpad.pad.entities.Pad
import fr.xsystems.scratchpad.pad.entities.PadMode
import fr.xsystems.scratchpad.pad.entities.PadUUID
import fr.xsystems.scratchpad.pad.gateway.PadRepositorySpy
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.from
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class RetrieveAPadByIDTest {
    val expected = Pad(
        PadUUID(UUID.randomUUID()),
        "My pad",
        PadMode.INSTANT_MESSENGER,
        UserUUID(UUID.randomUUID()),
        ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault())
    )

    @Test
    fun shouldFindAPadFromRepository() {
        val padRepo = PadRepositorySpy(
            getByIdStub = { uid -> expected.takeIf { it.id.uuid === uid } },
            checkExist = { false }
        )
        val uc = RetrieveAPadByID(padRepo)
        val req = RetrieveAPadByID.PadId(expected.id.uuid)

        val pad = uc.execute(req)

        assertThat(padRepo.getSpy.calledExactlyOnce()).isTrue
        assertThat(padRepo.getSpy.lastData).isEqualTo(expected.id.uuid)
        assertThat(pad).isNotNull
        assertThat(pad)
            .returns(expected.name, from { it.name })
            .returns(expected.mode, from { it.mode })
            .returns(expected.creation, from { it.creation })
            .returns(expected.id.uuid, from { it.id.uuid })
            .returns(expected.owner.uuid, from { it.owner.uuid })
    }

    @Test
    fun shouldThrowWhenNoPad() {
        val uuid = UUID.randomUUID()
        val padRepo = PadRepositorySpy()
        val uc = RetrieveAPadByID(padRepo)
        val req = RetrieveAPadByID.PadId(uuid)

        val notExist = assertThrows<RetrieveAPadByID.PadDoesNotExistException> {
            uc.execute(req)
        }

        assertThat(padRepo.getSpy.calledExactlyOnce()).isTrue
        assertThat(notExist.uuid).isEqualTo(uuid)
    }
}