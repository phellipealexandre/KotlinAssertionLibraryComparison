package com.phellipesilva.benchmarkable.scenario1

import org.junit.Test
import kotlin.test.junit.JUnitAsserter.assertEquals

class SimpleCalculatorKotlinJUnitTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertEquals(
            message = "sum must match",
            expected = 4,
            actual = sum
        )
    }
}