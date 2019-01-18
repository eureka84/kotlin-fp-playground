package com.eureka.arrow

import arrow.core.Predicate
import arrow.instances.semigroup
import arrow.typeclasses.Semigroup
import com.eureka.PredicateK
import com.eureka.andSemiGroup
import com.eureka.orSemiGroup
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

    @Test
    fun predicates() {
        val isGreaterThan3: Predicate<Int> = { x: Int -> x > 3 }
        val isEven: Predicate<Int> = { x: Int -> x % 2 == 0 }
        val semigroup: Semigroup<Predicate<Int>> = PredicateK.semigroup(Boolean.andSemiGroup())
        semigroup.run {
            assertTrue(isGreaterThan3.combine(isEven)(4))
        }
    }
}
