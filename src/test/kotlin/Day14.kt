import kotlin.test.Test
import kotlin.test.assertEquals

class Day14 {
    @Test
    fun `polymer template gives 1588`(){
        val result = polymerTemplate(input)
        assertEquals(1588L, result)
    }

    @Test
    fun `polymer template gives 2188189693529`(){
        val result = polymerTemplate(input, 40)
        assertEquals(2188189693529L, result)
    }

    companion object{
        val input = """
            NNCB

            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """.trimIndent()
    }
}