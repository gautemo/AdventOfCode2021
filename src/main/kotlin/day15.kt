import shared.Point
import shared.XYMap
import shared.getLines
import kotlin.math.abs

class CavernRisk(input: List<String>): XYMap<Int>(input, { c: Char -> c.digitToInt() }) {
    private val start = Point(0,0)
    private val goal = Point(width-1, height-1)

    private fun heuristic(a: Point, b: Point) = (abs(a.x - b.x) + abs(a.y - b.y)) * 5

    private fun reconstructPath(cameFrom: Map<Point, Point>, end: Point): Int {
        var totalPath = 0
        var current = end
        while (cameFrom.containsKey(current)){
            totalPath += getValue(current)
            current = cameFrom[current]!!
        }
        return totalPath
    }

    fun aStar(): Int{
        val openSet = mutableSetOf(start)
        val cameFrom = mutableMapOf<Point, Point>()
        val gScore = mutableMapOf(Pair(start, 0))
        val fScore = mutableMapOf(Pair(start, heuristic(start, goal)))
        while (openSet.isNotEmpty()){
            val current = openSet.minByOrNull { fScore[it]!! }!!
            if(current == goal) return reconstructPath(cameFrom, current)
            openSet.remove(current)
            for(neighbour in findAdjacentPoints(current)){
                val tentativeGScore = gScore[current]!! + getValue(neighbour)
                if(tentativeGScore < gScore[neighbour] ?: Int.MAX_VALUE){
                    cameFrom[neighbour] = current
                    gScore[neighbour] = tentativeGScore
                    fScore[neighbour] = tentativeGScore + heuristic(neighbour, goal)
                    if(!openSet.contains(neighbour)) openSet.add(neighbour)
                }
            }
        }
        throw Exception("should have found goal by now")
    }
}

fun toFullMap(input: List<String>): List<String>{
    fun repeat(line: String, nr: Int): String{
        return line.map {
            val increased = it.digitToInt() + nr
            if(increased > 9) increased % 9 else increased
        }.joinToString("")
    }

    val horizontalScaled = input.map {
        repeat(it, 0) + repeat(it, 1) + repeat(it, 2) + repeat(it, 3) + repeat(it, 4)
    }
    val fullMap = mutableListOf<String>()
    repeat(5){ i ->
        horizontalScaled.forEach { line ->
            fullMap.add(repeat(line, i))
        }
    }
    return fullMap
}

fun main(){
    val input = getLines("day15.txt")
    val cavern = CavernRisk(input)
    val task1 = cavern.aStar()
    println(task1)
    val fullMap = toFullMap(input)
    val fullCavern = CavernRisk(fullMap)
    val task2 = fullCavern.aStar()
    println(task2)
}