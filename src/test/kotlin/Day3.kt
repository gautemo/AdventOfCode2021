import kotlin.test.Test
import kotlin.test.assertEquals

class Day3 {
    @Test
    fun `power consumption is 198`(){
        val result = getPowerConsumption(input)
        assertEquals(198, result)
    }

    @Test
    fun `life support is 230`(){
        val result = getLifeSupport(input)
        assertEquals(230, result)
    }

    companion object{
        val input = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
        """.trimIndent().lines()
    }
}