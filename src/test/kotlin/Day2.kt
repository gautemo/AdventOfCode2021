import kotlin.test.Test
import kotlin.test.assertEquals

class Day2 {
    @Test
    fun `should produce 150`(){
        val result = movePart1(input)
        assertEquals(150, result)
    }

    @Test
    fun `part two should produce 900`(){
        val result = movePart2(input)
        assertEquals(900, result)
    }

    companion object {
        val input = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent().lines()
    }
}