package com.eureka

import arrow.core.Predicate
import arrow.typeclasses.Semigroup

object Syntax {

    fun Boolean.Companion.andSemiGroup(): Semigroup<Boolean> {
        return object : Semigroup<Boolean> {
            override fun Boolean.combine(b: Boolean): Boolean = this && b
        }
    }

    fun Boolean.Companion.orSemiGroup(): Semigroup<Boolean> {
        return object : Semigroup<Boolean> {
            override fun Boolean.combine(b: Boolean): Boolean = this || b
        }
    }

//    fun <T> Predicate<T>.semigroup(s: Semigroup<Boolean>): Semigroup<Predicate<T>> = object : Semigroup<Predicate<T>> {
//        override fun Predicate<T>.combine(other: Predicate<T>): Predicate<T> = { t: T ->
//            val originalPredicate = this
//            s.run {
//                originalPredicate(t).combine(other(t))
//            }
//        }
//    }

}