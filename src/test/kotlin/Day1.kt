import kotlin.test.Test
import kotlin.test.assertEquals

class Day1 {
    @Test
    fun `should increase 7 times`(){
        val result = countIncrease(input)
        assertEquals(7, result)
    }

    @Test
    fun `should increase 5 times in window mode`(){
        val windows = toWindows(input)
        val result = countIncrease(windows)
        assertEquals(5, result)
    }

    companion object {
        val input = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent().lines().map { it.toInt() }
    }
}