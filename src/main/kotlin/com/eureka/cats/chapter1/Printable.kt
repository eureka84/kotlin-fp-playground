package com.eureka.cats.chapter1

import arrow.core.Option

// @typeclass
interface Printable<A> {
    fun A.format(): String
}

fun <A> print(toPrint: A, printable: Printable<A>) = printable.run {
    println(toPrint.format())
}

fun <A> format(toFormat: A, printable: Printable<A>) = printable.run { toFormat.format() }

data class Person(val name: String, val surname: String) {
    companion object {
        val printable: Printable<Person> = object : PrintablePerson {}
    }
}

interface PrintablePerson : Printable<Person> {
    override fun Person.format(): String = "$name $surname"
}

interface PrintableForOption<A> : Printable<Option<A>> {

    val printable: Printable<A>

    override fun Option<A>.format(): String =
        this.fold(
            { "" },
            { a ->  printable.run { a.format() }}
        )

}


