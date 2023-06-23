package fr.xsystems.scratchpad.pad.delivery.providers

import fr.xsystems.scratchpad.pad.gateway.DateTimeProvider
import java.time.ZonedDateTime

class CurrentDateTimeProvider : DateTimeProvider {
    override fun now(): ZonedDateTime = ZonedDateTime.now()
}