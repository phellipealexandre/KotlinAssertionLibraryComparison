package com.phellipesilva.assertionbenchmark.scenario1

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import com.phellipesilva.benchmarkable.scenario1.SimpleCalculator
import org.junit.Rule
import org.junit.Test

class SimpleCalculatorAtriumInstrumentedTest {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun sum() = benchmarkRule.measureRepeated {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        expect(sum).toBe(4)
    }
}