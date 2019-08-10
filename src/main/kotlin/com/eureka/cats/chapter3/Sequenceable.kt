package com.eureka.cats.chapter3

import arrow.Kind
import arrow.typeclasses.Applicative

interface Sequenceable<F> {

    val applicative: Applicative<F>

    fun <A> List<Kind<F, A>>.sequence(): Kind<F, List<A>> {
        val initial = applicative.just<List<A>>(listOf())
        return this.fold(initial) { acc: Kind<F, List<A>>, curr: Kind<F, A> ->
            applicative.run {
                applicative.map(acc, curr) {(list, el) -> list + el}
            }
        }
    }
}