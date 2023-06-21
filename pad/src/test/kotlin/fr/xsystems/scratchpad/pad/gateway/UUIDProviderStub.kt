package fr.xsystems.scratchpad.pad.gateway

import fr.xsystems.scratchpad.testing.uuid.UUIDProviderHelper
import java.util.*

class UUIDProviderStub(vararg uuid: UUID) : UUIDProvider {
    val uuidProvider = UUIDProviderHelper(*uuid)
    override fun generate(): UUID = uuidProvider.nextUUID()
}