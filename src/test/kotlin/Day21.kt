import kotlin.test.Test
import kotlin.test.assertEquals

class Day21 {
    @Test
    fun `practice dirac dice end score is 739785`(){
        val startPos = startPositions(input)
        val deterministic = Dirac(startPos.first, startPos.second, DeterministicDice())
        val result = deterministic.playDeterministic()
        assertEquals(739785, result)
    }

    @Test
    fun `quantum dirac winner wins 444356092776315 times`(){
        val startPos = startPositions(input)
        val quantum = Dirac(startPos.first, startPos.second, QuantumDice())
        val result = quantum.playQuantum()
        assertEquals(444356092776315, result)
    }

    companion object{
        val input = """
            Player 1 starting position: 4
            Player 2 starting position: 8
        """.trimIndent()
    }
}