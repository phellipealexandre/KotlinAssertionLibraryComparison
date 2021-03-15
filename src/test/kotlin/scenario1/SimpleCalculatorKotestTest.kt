package scenario1

import io.kotest.matchers.be
import io.kotest.matchers.ints.beGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.Test

class SimpleCalculatorKotestTest {

    @Test
    fun sum() {
        val complexClass = SimpleCalculator()

        val sum = complexClass.sum(1,3)

        sum.shouldBe(4)
        sum shouldBe 4

        sum.should {
            be(4)
            beGreaterThan(3)
        }
    }
}