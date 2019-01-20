package com.eureka.arrow

import arrow.core.Predicate
import arrow.data.k
import arrow.instances.semigroup
import arrow.typeclasses.Semigroup
import com.eureka.*
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

    private val isGreaterThan3: Predicate<Int> = { x: Int -> x > 3 }
    private val isEven: Predicate<Int> = { x: Int -> x % 2 == 0 }

    @Test
    fun predicates() {
        val semiGroup: Semigroup<Predicate<Int>> = PredicateK.semigroup(Boolean.andSemiGroup())
        semiGroup.run {
            assertTrue(isGreaterThan3.combine(isEven)(4))
        }
    }

    @Test
    fun `list reduce`() {
        val predicates = listOf(isEven, isGreaterThan3)
        val intersection = predicates.k().reduce(all())
        val union = predicates.k().reduce(any())

        assertTrue(intersection(4))
        assertFalse(intersection(5))
        assertTrue(union(5))
    }
}
