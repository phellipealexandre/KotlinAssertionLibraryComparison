package com.phellipesilva.assertionbenchmark.scenario1

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import com.phellipesilva.benchmarkable.scenario1.SimpleCalculator
import org.junit.Rule
import org.junit.Test
import kotlin.test.junit.JUnitAsserter.assertEquals

class SimpleCalculatorKotlinJUnitInstrumentedTest {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun sum() = benchmarkRule.measureRepeated {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertEquals(
            message = "sum must match",
            expected = 4,
            actual = sum
        )
    }
}