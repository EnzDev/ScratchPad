package fr.xsystems.scratchpad.pad.usercases

import fr.xsystems.scratchpad.core.UseCase
import fr.xsystems.scratchpad.pad.entities.Pad
import java.util.*

class RetrieveAPadByID(
    private val padRepository: PadRepository,
) : UseCase<RetrieveAPadByID.PadId, Pad> {
    data class PadDoesNotExistException(val uuid: UUID): Exception()
    data class PadId(val uuid: UUID)
    interface PadRepository {
        fun getById(uuid: UUID): Pad?
    }

    override fun execute(request: PadId): Pad = padRepository.getById(request.uuid)
        ?: throw PadDoesNotExistException(request.uuid)
}