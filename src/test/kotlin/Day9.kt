import kotlin.test.Test
import kotlin.test.assertEquals

class Day9 {
    @Test
    fun `sum of the risk levels is 15`(){
        val result = findRiskLevelsSum(input)
        assertEquals(15, result)
    }

    @Test
    fun `largest basins multiplied is 1134`(){
        val result = findLargestBasinsMultiplied(input)
        assertEquals(1134, result)
    }

    companion object{
        val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent().lines()
    }
}