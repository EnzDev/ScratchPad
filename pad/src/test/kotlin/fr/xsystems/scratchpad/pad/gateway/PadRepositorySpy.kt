package fr.xsystems.scratchpad.pad.gateway

import fr.xsystems.scratchpad.pad.entities.Pad
import fr.xsystems.scratchpad.testing.spy.SavingSpy
import java.util.UUID

class PadRepositorySpy(
    val getByIdStub: (UUID) -> Pad? = {null},
    val checkExist: (UUID) -> Boolean = {false}
) : PadRepository {
    val saveSpy = SavingSpy<Pad>()
    val existSpy = SavingSpy<UUID>()
    val getSpy = SavingSpy<UUID>()

    override fun save(pad: Pad) = saveSpy.trigger(pad)
    override fun existById(uuid: UUID): Boolean {
        existSpy.trigger(uuid)
        return checkExist(uuid)
    }

    override fun getById(uuid: UUID): Pad? {
        getSpy.trigger(uuid)
        return getByIdStub.invoke(uuid)
    }
}