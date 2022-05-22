package com.phellipesilva.benchmarkable.scenario1

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.phellipesilva.benchmarkable.scenario1.SimpleCalculator
import org.junit.Test

class SimpleCalculatorAssertKTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertThat(sum).all {
            isEqualTo(4)
        }
    }
}