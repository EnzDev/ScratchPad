package fr.xsystems.scratchpad.pad.gateway

import java.time.ZonedDateTime

class DateTimeProviderStub(private val date: ZonedDateTime) : DateTimeProvider {
    override fun now(): ZonedDateTime = date
}