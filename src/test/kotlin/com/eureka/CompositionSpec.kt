package com.eureka

import com.eureka.ctfp.chapter1.Function1
import com.eureka.ctfp.chapter1.Prelude.id
import com.eureka.ctfp.chapter1.compose
import io.kotlintest.properties.forAll
import io.kotlintest.specs.StringSpec
import org.assertj.core.api.Assertions.assertThat

class CompositionSpec: StringSpec() {

    init {

        "identity checks for any kind of value" {
            val aString = "Eureka!"
            val aNumber = 3.14
            val anObject = Object(42)
            val aFunction: (Int) -> String = { n -> n.toString() }

            assertThat(aString).isEqualTo(id(aString))
            assertThat(aNumber).isEqualTo(id(aNumber))
            assertThat(anObject).isEqualTo(id(anObject))
            assertThat(aFunction).isEqualTo(id(aFunction))
        }

        "function composition respects identity" {
            forAll { n: Int ->
                val double = { a: Int -> 2 * a }
                val intIdentity: Function1<Int, Int> = ::id

                (intIdentity compose double)(n) == (double compose intIdentity)(n)
            }
        }
    }
}

data class Object<T>(val value: T)