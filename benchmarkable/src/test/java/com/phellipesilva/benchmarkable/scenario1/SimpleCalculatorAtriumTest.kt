package com.phellipesilva.benchmarkable.scenario1

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import org.junit.Test

class SimpleCalculatorAtriumTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        expect(sum).toBe(4)
    }
}