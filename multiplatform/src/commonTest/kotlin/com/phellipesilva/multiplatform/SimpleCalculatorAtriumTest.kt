package com.phellipesilva.multiplatform

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import kotlin.test.Test

class SimpleCalculatorAtriumTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        expect(sum).toBe(4)
    }
}