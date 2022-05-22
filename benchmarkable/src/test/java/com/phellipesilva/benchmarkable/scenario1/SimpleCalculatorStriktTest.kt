package com.phellipesilva.benchmarkable.scenario1

import org.junit.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class SimpleCalculatorStriktTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

       expect {
           that(sum).isEqualTo(4)
       }
    }
}