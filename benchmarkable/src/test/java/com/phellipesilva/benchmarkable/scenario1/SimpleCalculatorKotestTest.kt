package com.phellipesilva.benchmarkable.scenario1

import io.kotest.matchers.shouldBe
import org.junit.Test

class SimpleCalculatorKotestTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        sum shouldBe 4
    }
}