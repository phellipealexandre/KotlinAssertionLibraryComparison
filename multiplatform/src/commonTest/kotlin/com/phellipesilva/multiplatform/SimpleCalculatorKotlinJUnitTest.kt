package com.phellipesilva.multiplatform

import kotlin.test.Test
import kotlin.test.assertEquals

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