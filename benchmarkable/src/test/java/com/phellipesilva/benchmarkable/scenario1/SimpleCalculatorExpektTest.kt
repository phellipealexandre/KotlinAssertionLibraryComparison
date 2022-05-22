package com.phellipesilva.benchmarkable.scenario1

import com.winterbe.expekt.should
import org.junit.Test

class SimpleCalculatorExpektTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        sum.should.equal(4)
    }
}