import kotlin.test.Test
import kotlin.test.assertEquals

class Day25 {
    @Test
    fun `cucumbers stops after 58 steps`(){
        val result = cucumbersStopsAt(input)
        assertEquals(58, result)
    }

    companion object{
        val input = """
            v...>>.vv>
            .vv>>.vv..
            >>.>v>...v
            >>v>>.>.v.
            v>v.vv.v..
            >.>>..v...
            .vv..>.>v.
            v.v..>>v.v
            ....v..v.>
        """.trimIndent()
    }
}