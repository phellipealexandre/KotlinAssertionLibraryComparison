package com.phellipesilva.benchmarkable.scenario1

import com.varabyte.truthish.assertThat
import org.junit.Test

class SimpleCalculatorTruthishTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertThat(sum).isEqualTo(4)
    }
}