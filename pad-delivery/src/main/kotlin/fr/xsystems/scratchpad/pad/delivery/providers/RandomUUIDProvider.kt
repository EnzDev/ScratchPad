package fr.xsystems.scratchpad.pad.delivery.providers

import fr.xsystems.scratchpad.pad.gateway.UUIDProvider
import java.util.*

class RandomUUIDProvider : UUIDProvider {
    override fun generate(): UUID = UUID.randomUUID()
}