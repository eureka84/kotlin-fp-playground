package com.eureka.cats.chapter3

import arrow.core.Either
import arrow.core.EitherPartialOf
import arrow.core.fix
import arrow.instances.EitherApplicativeInstance
import arrow.typeclasses.Applicative
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.math.BigDecimal

class EitherListExtensionsTest {

    private val sequentialContext = object : Sequentiable<EitherPartialOf<Exception>> {
        override val applicative: Applicative<EitherPartialOf<Exception>> = object : EitherApplicativeInstance<Exception>{}
    }

    @Test
    fun sequenceEmpty() {
        sequentialContext.run {
            val sequenceResult: Either<Exception, List<String>> = listOf<Either<Exception, String>>().sequence().fix()
            val expected: Either<Exception, List<String>> = Either.right(listOf())
            assertThat(sequenceResult, equalTo(expected))
        }
    }

    @Test
    fun sequenceNonEmptyOnlyRight() {
        sequentialContext.run {
            val sequenceResult: Either<Exception, List<String>> = listOf<Either<Exception, String>>(
                Either.right("A"),
                Either.right("B")
            ).sequence().fix()

            val expected: Either<Exception, List<String>> = Either.right(listOf("A", "B"))
            assertThat(sequenceResult, equalTo(expected))
        }
    }

    @Test
    fun sequenceNonEmptyWithALeft() {
        sequentialContext.run {
            val sequenceResult: Either<Exception, List<BigDecimal>> = listOf<Either<MyException, BigDecimal>>(
                Either.right(BigDecimal("5")),
                Either.left(MyException("ouch!")),
                Either.right(BigDecimal("7"))
            ).sequence().fix()

            val expected: Either<Exception, List<BigDecimal>> = Either.left(MyException("ouch!"))
            assertThat(sequenceResult, equalTo(expected))
        }
    }

    data class MyException(override val message: String) : Exception(message)
}