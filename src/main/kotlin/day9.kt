import shared.Point
import shared.XYMap
import shared.getLines

fun findRiskLevelsSum(input: List<String>): Int{
    val map = LavaTubes(input)
    return map.getLowPoints().sumOf { map.getValue(it) + 1 }
}

fun findLargestBasinsMultiplied(input: List<String>): Int{
    val map = LavaTubes(input)
    val basinSizes = map.findBasins()
    return basinSizes.sortedDescending().take(3).reduce { acc, i -> acc * i }
}

fun main(){
    val input = getLines("day9.txt")
    val task1 = findRiskLevelsSum(input)
    println(task1)
    val task2 = findLargestBasinsMultiplied(input)
    println(task2)
}

private class LavaTubes(input: List<String>): XYMap<Int>(input, { c: Char -> c.digitToInt() }) {

    private fun lowPoint(point: Point) = findAdjacentPoints(point).all { getValue(point) < getValue(it) }

    fun getLowPoints() = allPoints().filter { lowPoint(it) }

    fun findBasins() = getLowPoints().map { findBasinSize(it) }

    private fun findBasinSize(lowPoint: Point): Int{
        val hasChecked = mutableListOf(lowPoint)
        var toCheck = findAdjacentPoints(lowPoint).filter { getValue(it) != 9 }
        var size = 1
        while(toCheck.isNotEmpty()){
            size += toCheck.size
            hasChecked.addAll(toCheck)
            toCheck = toCheck.flatMap { point -> findAdjacentPoints(point) }.toSet().filter { !hasChecked.contains(it) && getValue(it) != 9 }
        }
        return size
    }
}
