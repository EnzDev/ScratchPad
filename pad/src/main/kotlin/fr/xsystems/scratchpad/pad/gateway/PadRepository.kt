package fr.xsystems.scratchpad.pad.gateway

import fr.xsystems.scratchpad.pad.usercases.CreateANewPad
import fr.xsystems.scratchpad.pad.usercases.CreateUniquePadUUID
import fr.xsystems.scratchpad.pad.usercases.RetrieveAPadByID

interface PadRepository :
    CreateANewPad.PadRepository,
    CreateUniquePadUUID.PadRepository,
    RetrieveAPadByID.PadRepository