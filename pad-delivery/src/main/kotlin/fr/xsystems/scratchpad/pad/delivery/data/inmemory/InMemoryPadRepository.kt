package fr.xsystems.scratchpad.pad.delivery.data.inmemory

import fr.xsystems.scratchpad.pad.delivery.data.entities.PadEntity
import fr.xsystems.scratchpad.pad.entities.Pad
import fr.xsystems.scratchpad.pad.gateway.PadRepository
import java.util.*


class InMemoryPadRepository : PadRepository {
    private val pads = mutableMapOf<UUID, PadEntity>()
    override fun save(pad: Pad) {
        pads[pad.id.uuid] = PadEntity.fromDomain(pad)
    }

    override fun existById(uuid: UUID) = uuid in pads

    override fun getById(uuid: UUID): Pad? = pads[uuid]
        ?.let(PadEntity.Companion::toDomain)
}