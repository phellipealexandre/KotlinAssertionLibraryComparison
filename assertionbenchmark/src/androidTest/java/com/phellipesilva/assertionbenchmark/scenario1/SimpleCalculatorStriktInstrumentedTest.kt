package com.phellipesilva.assertionbenchmark.scenario1

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import com.phellipesilva.benchmarkable.scenario1.SimpleCalculator
import org.junit.Rule
import org.junit.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class SimpleCalculatorStriktInstrumentedTest {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun sum() = benchmarkRule.measureRepeated {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

       expect {
           that(sum).isEqualTo(4)
       }
    }
}