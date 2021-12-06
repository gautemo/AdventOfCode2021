import kotlin.test.Test
import kotlin.test.assertEquals

class Day6 {
    @Test
    fun `should have 5934 lanternfish after 80 days`(){
        val result = simulateLanternfishGrowth(input)
        assertEquals(5934, result)
    }

    @Test
    fun `should have 26984457539 lanternfish after 256 days`(){
        val result = simulateLanternfishGrowth(input, 256)
        assertEquals(26984457539, result)
    }

    companion object{
        const val input = "3,4,3,1,2"
    }
}