package scenario1

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.describe
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import org.junit.Test

class SimpleCalculatorHamkrestTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        assertThat(sum, equalTo(4) and greaterThan(3))
    }
}