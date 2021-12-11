import shared.Point
import shared.XYMap
import shared.getLines

fun countFlashes(input: List<String>): Int{
    val map = Octopuses(input)
    repeat(100){
        map.round()
    }
    return map.flashes
}

fun allFlashed(input: List<String>): Int{
    var count = 0
    val map = Octopuses(input)
    while(!map.allFlashed()){
        map.round()
        count++
    }
    return count
}

private class Octopuses(input: List<String>): XYMap<Int>(input, { c: Char -> c.digitToInt() }){
    var flashes = 0

    private fun bump(point: Point){
        val charge = getValue(point) + 1
        setValue(point, charge)
        if(charge == 10) {
            flashes++
            findAdjacentPoints(point, true).forEach { bump(it) }
        }
    }

    fun round(){
        allPoints().forEach { bump(it) }
        cleanRound()
    }

    private fun cleanRound(){
        allPoints().forEach {
            if(getValue(it) > 9) setValue(it, 0)
        }
    }

    fun allFlashed() = allPoints().map { getValue(it) }.all { it == 0 }
}

fun main(){
    val input = getLines("day11.txt")
    val task1 = countFlashes(input)
    println(task1)
    val task2 = allFlashed(input)
    println(task2)
}