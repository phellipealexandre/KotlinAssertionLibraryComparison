package com.phellipesilva.benchmarkable.scenario1

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SimpleCalculatorAssertJTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertThat(sum).isEqualTo(4)
    }
}