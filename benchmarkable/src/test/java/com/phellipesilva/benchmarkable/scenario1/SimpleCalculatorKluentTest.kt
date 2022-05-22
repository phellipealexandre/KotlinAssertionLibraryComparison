package com.phellipesilva.benchmarkable.scenario1

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class SimpleCalculatorKluentTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        sum shouldBeEqualTo 4
    }
}