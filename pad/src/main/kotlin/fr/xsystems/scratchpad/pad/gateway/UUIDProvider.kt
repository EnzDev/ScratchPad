package fr.xsystems.scratchpad.pad.gateway

import fr.xsystems.scratchpad.pad.usercases.CreateUniquePadUUID

interface UUIDProvider:
    CreateUniquePadUUID.UUIDProvider