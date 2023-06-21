package fr.xsystems.scratchpad.pad.usercases

import fr.xsystems.scratchpad.administration.entities.UserUUID
import fr.xsystems.scratchpad.core.UseCase
import fr.xsystems.scratchpad.pad.entities.Pad
import fr.xsystems.scratchpad.pad.entities.PadMode
import fr.xsystems.scratchpad.pad.entities.PadUUID
import java.time.ZonedDateTime
import java.util.*

class CreateANewPad(
    private val padRepository: PadRepository,
    private val dateTimeProvider: DateTimeProvider,
): UseCase<CreateANewPad.PadCreation, Pad> {
    data class DuplicatedPadUUIDException(val uuid: UUID): Exception()
    interface PadRepository {
        fun save(pad: Pad)
        fun existById(uuid: UUID): Boolean
    }

    interface DateTimeProvider {
        fun now(): ZonedDateTime
    }

    data class PadCreation(
        val uuid: UUID,
        val name: String,
        val creator: UserUUID,
        val mode: PadMode = PadMode.INSTANT_MESSENGER
    )

    override fun execute(request: PadCreation): Pad {
        if (padRepository.existById(request.uuid)) throw DuplicatedPadUUIDException(request.uuid)
        val pad = Pad(
            PadUUID(request.uuid),
            request.name,
            request.mode,
            request.creator,
            dateTimeProvider.now()
        )

        padRepository.save(pad)
        return pad
    }
}