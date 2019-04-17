package com.eureka.cats.chapter1

import arrow.core.Option
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PrintableTest {

    @Test
    fun `print option of strings`() {

        val printable = object : PrintableForOption<Person> {
            override val printable: Printable<Person> = Person.printable
        }

        assertThat(
            format(Option(Person("Paolo", "Sciarra")), printable)
        ).isEqualTo("Paolo Sciarra")
    }
}