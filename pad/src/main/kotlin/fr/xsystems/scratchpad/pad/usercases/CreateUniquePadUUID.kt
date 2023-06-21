package fr.xsystems.scratchpad.pad.usercases

import fr.xsystems.scratchpad.core.UseCase
import fr.xsystems.scratchpad.pad.entities.PadUUID
import java.util.UUID

class CreateUniquePadUUID(
    private val padRepository: PadRepository,
    private val uuidProvider: UUIDProvider
): UseCase<Unit, PadUUID> {
    interface PadRepository {
        fun existById(uuid: UUID): Boolean
    }

    interface UUIDProvider {
        fun generate(): UUID
    }

    override fun execute(request: Unit): PadUUID {
        var generated = uuidProvider.generate()
        while(padRepository.existById(generated)) {
            generated = uuidProvider.generate()
        }
        return PadUUID(generated)
    }
}