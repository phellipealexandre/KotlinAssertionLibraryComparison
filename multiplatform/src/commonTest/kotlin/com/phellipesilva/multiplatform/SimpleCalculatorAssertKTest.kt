package com.phellipesilva.multiplatform

import kotlin.test.Test
import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo

class SimpleCalculatorAssertKTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertThat(sum).all {
            isEqualTo(4)
        }
    }
}