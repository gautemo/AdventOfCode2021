import kotlin.test.Test
import kotlin.test.assertEquals

class Day12 {
    @Test
    fun `small cave has 10 distinct paths`(){
        val cave = CaveNavigate(input)
        val result = cave.findDistinctPaths()
        assertEquals(10, result)
    }

    @Test
    fun `small cave has 36 distinct paths`(){
        val cave = CaveNavigate(input, true)
        val result = cave.findDistinctPaths()
        assertEquals(36, result)
    }

    companion object{
        val input = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent().lines()
    }
}