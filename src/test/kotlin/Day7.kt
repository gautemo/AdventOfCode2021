import kotlin.test.Test
import kotlin.test.assertEquals

class Day7 {
    @Test
    fun `lowest cost is 37`(){
        val result = alignCrabs(input)
        assertEquals(37, result)
    }

    @Test
    fun `lowest cost is 168`(){
        val result = alignCrabs(input, true)
        assertEquals(168, result)
    }

    @Test
    fun `move 16 to 5 cost 66`(){
        val result = nthTriangularNumber(16-5)
        assertEquals(66, result)
    }

    companion object{
        const val input = "16,1,2,0,4,2,7,1,2,14"
    }
}