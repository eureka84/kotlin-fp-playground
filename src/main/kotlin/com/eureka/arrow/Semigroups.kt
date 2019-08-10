package com.eureka.arrow

import arrow.core.Predicate
import arrow.typeclasses.Semigroup


fun Boolean.Companion.andSemiGroup(): Semigroup<Boolean> =
    object : Semigroup<Boolean> {
        override fun Boolean.combine(b: Boolean): Boolean = this && b
    }

fun Boolean.Companion.orSemiGroup(): Semigroup<Boolean> =
    object : Semigroup<Boolean> {
        override fun Boolean.combine(b: Boolean): Boolean = this || b
    }

class PredicateK {
    companion object {
        fun <T> semiGroup(s: Semigroup<Boolean>): Semigroup<Predicate<T>> =
            object : Semigroup<Predicate<T>> {
                override fun Predicate<T>.combine(b: Predicate<T>): Predicate<T> = { t: T ->
                    val originalPredicate = this
                    s.run {
                        originalPredicate(t).combine(b(t))
                    }
                }
            }
    }
}

fun <T> List<T>.reduce(s: Semigroup<T>): T {
    val l = this
    return s.run {
        l.reduce { a, e -> a.combine(e) }
    }
}

fun <T> all() = PredicateK.semiGroup<T>(Boolean.andSemiGroup())
fun <T> any() = PredicateK.semiGroup<T>(Boolean.orSemiGroup())

fun <T> List<T>.all(p: Predicate<T>): Boolean {
    val self = this
    return Boolean.andSemiGroup().run {
        self.fold(true) { a, e -> a.combine(p(e)) }
    }
}

fun <T> List<T>.any(p: Predicate<T>): Boolean {
    val self = this
    return Boolean.orSemiGroup().run {
        self.fold(false) { a, e -> a.combine(p(e)) }
    }
}
