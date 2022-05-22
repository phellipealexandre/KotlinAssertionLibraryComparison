package com.phellipesilva.multiplatform

import org.amshove.kluent.shouldBeEqualTo
import kotlin.test.Test

class SimpleCalculatorKluentTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        sum shouldBeEqualTo 4
    }
}