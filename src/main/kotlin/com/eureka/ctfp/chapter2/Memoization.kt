package com.eureka.ctfp.chapter2

typealias Function1<Input, Output> = (Input) -> Output

fun <Input, Output> Function1<Input, Output>.memoize(): Function1<Input, Output> {
    val original = this
    val storage: MutableMap<Input, Output> = mutableMapOf()
    return { i ->
        val stored = storage[i]
        if (stored == null) {
            val value = original(i)
            storage[i] = value
            value
        } else stored
    }
}