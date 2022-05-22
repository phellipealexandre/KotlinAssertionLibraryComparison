package com.phellipesilva.assertionbenchmark.scenario1

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.phellipesilva.benchmarkable.scenario1.SimpleCalculator
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleCalculatorAssertJInstrumentedTest {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun sum() = benchmarkRule.measureRepeated {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertThat(sum).isEqualTo(4)
    }
}