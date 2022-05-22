package com.phellipesilva.benchmarkable.scenario1

import org.junit.Assert.assertEquals
import org.junit.Test

class SimpleCalculatorJUnitTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertEquals(4, sum)
    }
}