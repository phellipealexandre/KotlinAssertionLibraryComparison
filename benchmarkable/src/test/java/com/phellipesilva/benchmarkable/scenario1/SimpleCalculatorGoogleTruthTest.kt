package com.phellipesilva.benchmarkable.scenario1

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SimpleCalculatorGoogleTruthTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertThat(sum).isEqualTo(4)
    }
}