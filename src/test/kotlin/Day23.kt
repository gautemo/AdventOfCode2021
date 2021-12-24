import kotlin.test.Test
import kotlin.test.assertEquals

class Day23 {
    @Test
    fun `sort with least energy 12521`(){
        val map = AmphipodAStar(input)
        val result = map.aStar()
        assertEquals(12521, result)
    }

    @Test
    fun `sort with least energy 44169`(){
        val map = AmphipodAStar(withFoldInput(input))
        val result = map.aStar()
        assertEquals(44169, result)
    }

    companion object{
        val input = """
            #############
            #...........#
            ###B#C#B#D###
              #A#D#C#A#
              #########
        """.trimIndent()
    }
}