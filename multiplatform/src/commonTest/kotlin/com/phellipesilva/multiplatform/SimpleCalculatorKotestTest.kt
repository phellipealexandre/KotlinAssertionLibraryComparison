package com.phellipesilva.multiplatform

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class SimpleCalculatorKotestTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        sum shouldBe 4
    }
}