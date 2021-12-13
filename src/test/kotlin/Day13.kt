import kotlin.test.Test
import kotlin.test.assertEquals

class Day13 {
    @Test
    fun `17 dots after one fold`(){
        val origami = Origami(input)
        origami.fold()
        val result = origami.dots()
        origami.foldAll()
        origami.print()
        assertEquals(17, result)
    }

    companion object{
        val input = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0

            fold along y=7
            fold along x=5
        """.trimIndent()
    }
}