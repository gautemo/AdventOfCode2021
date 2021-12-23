import shared.getText

class Dirac(private val startPlayer1: Int, private val startPlayer2: Int, private val dice: Dice){
    private val cache = mutableMapOf<DiracState, Wins>()

    private fun playerTurn(state: DiracState, steps: Int){
        val currentPlayer = if(state.player1Turn) state.player1 else state.player2
        currentPlayer.move(steps)
        currentPlayer.addScore()
        state.player1Turn = !state.player1Turn
    }

    fun playDeterministic(): Int{
        val state = DiracState(DiracPlayer(1, startPlayer1, 0), DiracPlayer(2, startPlayer2, 0), true, 1000)
        while(state.getWinner() == null){
            playerTurn(state, dice.throw1x())
        }
        return state.getLooser()!!.score * dice.rolled
    }

    fun playQuantum(): Long{
        val state = DiracState(DiracPlayer(1, startPlayer1, 0), DiracPlayer(2, startPlayer2, 0), true, 21)
        val wins = findWins(state)
        return maxOf(wins.player1, wins.player2)
    }

    private fun findWins(state: DiracState): Wins{
        if(state.getWinner()?.nr == 1) return Wins(1, 0)
        if(state.getWinner()?.nr == 2) return Wins(0, 1)
        if(cache.containsKey(state)) return cache[state]!!
        val wins = Wins(0, 0)
        for(steps in dice.throw3x()){
            val copyState = state.copy(player1 = state.player1.copy(), player2 = state.player2.copy())
            playerTurn(copyState, steps)
            val newWins = findWins(copyState)
            wins.player1 += newWins.player1
            wins.player2 += newWins.player2
        }
        cache[state] = wins
        return wins
    }
}

interface Dice{
    var rolled: Int
    fun throw1x(): Int
    fun throw3x(): List<Int>
}

class DeterministicDice: Dice{
    private var value = 1
        get(){
            return field.also {
                rolled++
                field++
                if(field == 101) field = 1
            }
        }
    override var rolled = 0
    override fun throw1x() = value + value + value
    override fun throw3x() = throw Exception("should not be used")
}

class QuantumDice: Dice{
    override var rolled = 0
    override fun throw3x(): List<Int> {
        val sumOptions = mutableListOf<Int>()
        for(d1 in 1..3) {
            for (d2 in 1..3) {
                for (d3 in 1..3) {
                    sumOptions.add(d1+d2+d3)
                }
            }
        }
        return sumOptions
    }
    override fun throw1x() = throw Exception("should not be used")
}

fun main(){
    val input = getText("day21.txt")
    val startPos = startPositions(input)
    val deterministic = Dirac(startPos.first, startPos.second, DeterministicDice())
    val task1 = deterministic.playDeterministic()
    println(task1)
    val quantum = Dirac(startPos.first, startPos.second, QuantumDice())
    val task2 = quantum.playQuantum()
    println(task2)
}

fun startPositions(input: String): Pair<Int, Int> {
    val regex = Regex("""\d+$""")
    val startPlayer1 = regex.find(input.lines()[0])!!.value.toInt()
    val startPlayer2 = regex.find(input.lines()[1])!!.value.toInt()
    return Pair(startPlayer1, startPlayer2)
}

private data class DiracState(val player1: DiracPlayer, val player2: DiracPlayer, var player1Turn: Boolean, val winAt: Int = 1000){
    fun getWinner(): DiracPlayer?{
        if(player1.score >= winAt) return player1
        if(player2.score >= winAt) return player2
        return null
    }
    fun getLooser(): DiracPlayer?{
        if(player1.score >= winAt) return player2
        if(player2.score >= winAt) return player1
        return null
    }
}
private data class DiracPlayer(val nr: Int, var pos: Int, var score: Int){
    fun move(times: Int){
        pos = (pos + times) % 10
        if(pos == 0) pos = 10
    }
    fun addScore(){
        score += pos
    }
}
private data class Wins(var player1: Long, var player2: Long)