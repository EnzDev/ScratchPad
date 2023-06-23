package fr.xsystems.scratchpad.pad.delivery.data.entities

import fr.xsystems.scratchpad.administration.entities.UserUUID
import fr.xsystems.scratchpad.pad.entities.Pad
import fr.xsystems.scratchpad.pad.entities.PadMode
import fr.xsystems.scratchpad.pad.entities.PadUUID
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

data class PadEntity(
    val id: UUID,
    val name: String,
    val mode: String,
    val owner: UUID,
    val creation: Instant?
) {
    companion object {
        fun toDomain(pad: PadEntity) = Pad(
            PadUUID(pad.id),
            pad.name,
            PadMode.valueOf(pad.mode),
            UserUUID(pad.owner),
            ZonedDateTime.ofInstant(pad.creation, ZoneId.systemDefault())
        )

        fun fromDomain(pad: Pad) = PadEntity(
            pad.id.uuid,
            pad.name,
            pad.mode.name,
            pad.owner.uuid,
            pad.creation?.toInstant()
        )
    }
}