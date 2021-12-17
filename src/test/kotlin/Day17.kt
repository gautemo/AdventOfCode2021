import kotlin.test.Test
import kotlin.test.assertEquals

class Day17 {
    @Test
    fun `highest y is 45`(){
        val result = highestPosTrajectory(input)
        assertEquals(45, result)
    }

    @Test
    fun `nr hits is 112`(){
        val result = nrHitTrajectories(input)
        assertEquals(112, result)
    }

    companion object{
        const val input = "target area: x=20..30, y=-10..-5"
    }
}