package com.eureka.cats.chapter3

import arrow.Kind
import arrow.typeclasses.Monad

interface Sequentiable<F> {

    val monad: Monad<F>

    fun <A> List<Kind<F, A>>.sequence(): Kind<F, List<A>> {
        val initial = monad.just<List<A>>(listOf())
        return this.fold(initial) { acc: Kind<F, List<A>>, curr: Kind<F, A> ->
            monad.run {
                acc.flatMap { list ->
                    curr.map { el ->
                        list + el
                    }
                }
            }
        }
    }
}