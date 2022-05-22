package com.phellipesilva.assertionbenchmark.scenario1

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.phellipesilva.benchmarkable.scenario1.SimpleCalculator
import org.junit.Rule
import org.junit.Test

class SimpleCalculatorAssertKInstrumentedTest {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun sum() = benchmarkRule.measureRepeated {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertThat(sum).all {
            isEqualTo(4)
        }
    }
}