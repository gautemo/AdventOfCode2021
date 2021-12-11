import kotlin.test.Test
import kotlin.test.assertEquals

class Day11 {
    @Test
    fun `100 steps gives 1656 flashes`(){
        val result = countFlashes(input)
        assertEquals(1656, result)
    }

    @Test
    fun `all flashes on 195`(){
        val result = allFlashed(input)
        assertEquals(195, result)
    }

    companion object{
        val input = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent().lines()
    }
}