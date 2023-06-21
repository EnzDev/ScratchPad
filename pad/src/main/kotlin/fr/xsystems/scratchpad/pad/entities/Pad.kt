package fr.xsystems.scratchpad.pad.entities

import fr.xsystems.scratchpad.administration.entities.UserUUID
import java.time.ZonedDateTime
import java.util.UUID

data class PadUUID(val uuid: UUID)
data class Pad(
    val id: PadUUID,
    val name: String,
    val mode: PadMode,
    val owner: UserUUID,
    val creation: ZonedDateTime?
)

enum class PadMode {
    INSTANT_MESSENGER,
    BOARD,
    SMALL_BOARD,
    GALLERY,
}