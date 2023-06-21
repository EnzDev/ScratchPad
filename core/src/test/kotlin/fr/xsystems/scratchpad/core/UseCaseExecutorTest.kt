package fr.xsystems.scratchpad.core

import fr.xsystems.scratchpad.testing.spy.SavingSpy
import fr.xsystems.scratchpad.testing.spy.SimpleSpy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UseCaseExecutorTest {
    val executor: UseCaseExecutor = UseCaseExecutorImp()

    @Test
    fun callUCWithNoArgsNoReturn() {
        val ucNoArgsNoReturn = object : UseCase<Unit, Unit> {
            val spy = SimpleSpy()

            override fun execute(request: Unit) = spy.trigger()
        }

        executor.invoke(useCase = ucNoArgsNoReturn)
            .toCompletableFuture()
            .get()

        assertThat(ucNoArgsNoReturn.spy.calledExactlyOnce()).isTrue
    }

    @Test
    fun callUCWithArgsNoReturn() {
        val ucNoReturn = object : UseCase<String, Unit> {
            val spy = SavingSpy<String>()
            override fun execute(request: String) = spy.trigger(request)
        }
        val input = "myInput"

        executor.invoke(
            useCase = ucNoReturn,
            requestDto = input,
            requestConverter = { it }
        )
            .toCompletableFuture()
            .get()

        assertThat(ucNoReturn.spy.lastData).isEqualTo(input)
    }

    @Test
    fun callUCWithNoArgsAndReturn() {
        val ucNoArgs = object : UseCase<Unit, String> {
            override fun execute(request: Unit) = "fixed"
        }

        val output = executor.invoke(
            useCase = ucNoArgs,
            responseConverter = { it }
        )
            .toCompletableFuture()
            .get()

        assertThat(output).isEqualTo("fixed")
    }

    @Test
    fun callUCWithArgsAndReturn() {
        val ucCompute = object : UseCase<Pair<String, String>, String> {
            override fun execute(request: Pair<String, String>) =
                request.first + request.second
        }

        val output = executor.invoke(
            useCase = ucCompute,
            requestDto = "a" to "b",
            requestConverter = { it },
            responseConverter = { it }
        )
            .toCompletableFuture()
            .get()

        assertThat(output).isEqualTo("ab")
    }

    @Test
    fun appliesInputTransformer() {
        data class DToObj(var param: String)
        data class DomainObj(var attribute: String)

        val ucWithDomainModel = object : UseCase<DomainObj, Unit> {
            val spy = SavingSpy<DomainObj>()

            override fun execute(request: DomainObj) = spy.trigger(request)
        }
        val request = DToObj("test")

        executor.invoke(
            useCase = ucWithDomainModel,
            requestDto = request,
            requestConverter = { DomainObj(it.param) },
        )
            .toCompletableFuture()
            .get()

        assertThat(ucWithDomainModel.spy.lastData?.attribute).isEqualTo(request.param)
    }

    @Test
    fun appliesOutputTransformer() {
        data class DToObj(var param: String)
        data class DomainObj(var attribute: String)

        val theReturnedObject = DomainObj("test")
        val ucWithDomainModel = object : UseCase<Unit, DomainObj> {
            val spy = SimpleSpy()

            override fun execute(request: Unit) = theReturnedObject
                .also { spy.trigger() }
        }

        val response = executor.invoke(
            useCase = ucWithDomainModel,
            responseConverter = { DToObj(it.attribute) },
        )
            .toCompletableFuture()
            .get()

        assertThat(response).isInstanceOf(DToObj::class.java)
        assertThat(response.param).isEqualTo(theReturnedObject.attribute)
    }
}