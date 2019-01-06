package com.eureka.ctfp.chapter1

typealias Function1<Input, Output> = (Input) -> Output
infix fun <A, B, C> Function1<B, C>.compose(g: (A) -> B): (A) -> C = { a -> this(g(a)) }

object Prelude {
    fun <T> id(value: T): T = value
}
