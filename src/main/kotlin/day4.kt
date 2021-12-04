import shared.getText

fun playBingo(input: String, looseMode: Boolean = false): Int{
    val sections = input.trim().split("\n\n")
    val numbers = sections.first().split(",").map { it.toInt() }
    val boards = sections.drop(1).map { Board(it) }
    for(number in numbers){
        for(board in boards.filter { !it.bingo }){
            board.playNumber(number)
            if(board.bingo){
                if(!looseMode || boards.all { it.bingo }){
                    return board.uncheckedSum() * number
                }
            }
        }
    }
    throw Exception("Someone should have won by now")
}

class Board(input: String){
    val lines = input.split("\n").map { line -> line.trim().split(Regex("\\s+")).map { number -> BingoNumber(number.toInt()) } }
    var bingo = false
        private set

    fun playNumber(number: Int){
        lines.forEach { line -> line.find { bingoNumber -> bingoNumber.number == number }?.checked = true }
        bingo = horizontalWin() || verticalWin()
    }

    private fun horizontalWin() =  lines.any { line -> line.all { bingoNumber ->  bingoNumber.checked } }
    private fun verticalWin(): Boolean{
        val length = lines.first().size
        for(i in 0 until length){
            if(lines.all { line -> line[i].checked }) return true
        }
        return false
    }

    fun uncheckedSum(): Int{
        return lines.sumOf { line -> line.filter { bingoNumber ->  !bingoNumber.checked }.sumOf { bingoNumber -> bingoNumber.number } }
    }
}

data class BingoNumber(val number: Int, var checked: Boolean = false)

fun main(){
    val input = getText("day4.txt")
    val result = playBingo(input)
    println(result)
    val resultLoosingBoard = playBingo(input, true)
    println(resultLoosingBoard)
}