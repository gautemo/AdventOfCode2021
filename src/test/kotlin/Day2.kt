import kotlin.test.Test
import kotlin.test.assertEquals

class Day2 {
    @Test
    fun `should produce 150`(){
        val result = moveProduces(input)
        assertEquals(150, result)
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