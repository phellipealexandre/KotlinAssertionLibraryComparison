package com.phellipesilva.assertionbenchmark.scenario1

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import com.phellipesilva.benchmarkable.scenario1.SimpleCalculator
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class SimpleCalculatorKluentInstrumentedTest {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun sum() = benchmarkRule.measureRepeated {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        sum.shouldBeEqualTo(4)
    }
}