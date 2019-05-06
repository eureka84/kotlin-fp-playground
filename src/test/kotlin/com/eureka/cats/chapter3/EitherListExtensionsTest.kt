package com.eureka.cats.chapter3

import arrow.core.*
import arrow.instances.EitherMonadInstance
import arrow.typeclasses.Monad
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.math.BigDecimal

class EitherListExtensionsTest {

    private val sequentiable: Sequentiable<EitherPartialOf<Exception>> = object : Sequentiable<EitherPartialOf<Exception>> {
        override val monad: Monad<EitherPartialOf<Exception>> = object : EitherMonadInstance<Exception> {}
    }

    @Test
    fun sequenceEmpty() {
        sequentiable.run {
            val sequenceResult: Either<Exception, List<String>> = listOf<Either<Exception, String>>().sequence().fix()
            val expected: Either<Exception, List<String>> = Either.right(listOf())
            assertThat(sequenceResult, equalTo(expected))
        }
    }

    @Test
    fun sequenceNonEmptyOnlyRight() {

        sequentiable.run {
            val sequenceResult: Either<Exception, List<String>> = listOf(
                Either.right("A"),
                Either.right("B")
            ).sequence().fix()

            val expected: Either<Exception, List<String>> = Either.right(listOf("A", "B"))
            assertThat(sequenceResult, equalTo(expected))
        }

    }

    @Test
    fun sequenceNonEmptyWithALeft() {
        sequentiable.run {
            val sequenceResult: Either<Exception, List<BigDecimal>> = listOf(
                Either.right(BigDecimal("5")),
                Either.left(MyException("ouch!")),
                Either.right(BigDecimal("7"))
            ).sequence().fix()

            val expected: Either<Exception, List<BigDecimal>> = Either.left(MyException("ouch!"))
            assertThat(sequenceResult, equalTo(expected))
        }
    }

    data class MyException(override val message: String): Exception(message)
}