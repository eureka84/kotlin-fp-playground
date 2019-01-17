package com.eureka.arrow

import arrow.instances.semigroup
import com.eureka.Syntax
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SemigroupsTest {

    @Test
    fun ints() {
        Int.semigroup().run {
            assertThat(2.combine(3)).isEqualTo(5)
        }
    }

    @Test
    fun booleans() {
        Syntax.run {
            Boolean.andSemiGroup().run {
                assertTrue(true.combine(true))
                assertFalse(true.combine(false))
                assertFalse(false.combine(true))
                assertFalse(false.combine(false))
            }

            Boolean.orSemiGroup().run {
                assertTrue(true.combine(true))
                assertTrue(true.combine(false))
                assertTrue(false.combine(true))
                assertFalse(false.combine(false))
            }
        }
    }

}
