package fr.xsystems.scratchpad.testing.spy

class SavingSpy<T> : SimpleSpy() {
    var lastData: T? = null
    fun trigger(data: T) {
        lastData = data
        super.trigger()
    }
}