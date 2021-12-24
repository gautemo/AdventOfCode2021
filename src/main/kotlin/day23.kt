import shared.Point
import shared.getText
import kotlin.math.abs

class AmphipodAStar(val input: String){
    private fun heuristic(map: String) = AmphipodCave(map).heurestic()

    fun aStar(): Int{
        val openSet = mutableSetOf(input)
        val cameFrom = mutableMapOf<String, String>()
        val gScore = mutableMapOf(Pair(input, 0))
        val fScore = mutableMapOf(Pair(input, heuristic(input)))
        while (openSet.isNotEmpty()){
            val current = openSet.minByOrNull { fScore[it]!! }!!
            val currentMap = AmphipodCave(current)
            if(currentMap.goalReached()) return gScore[current]!!
            openSet.remove(current)
            for((neighbour, energy) in currentMap.possibleMoves()){
                val tentativeGScore = gScore[current]!! + energy
                if(tentativeGScore < (gScore[neighbour] ?: Int.MAX_VALUE)){
                    cameFrom[neighbour] = current
                    gScore[neighbour] = tentativeGScore
                    fScore[neighbour] = tentativeGScore + heuristic(neighbour)
                    if(!openSet.contains(neighbour)) openSet.add(neighbour)
                }
            }
        }
        throw Exception("should have found goal by now")
    }
}

class AmphipodCave(val input: String){
    private val points: List<CavePoint>
    private val width = input.lines().first().length
    private val height = input.lines().size
    private val goalDepth = if(height == 5) 2 else 4
    init {
        val list = mutableListOf<CavePoint>()
        for(y in 0 until height){
            for(x in 0 until width){
                val p = when(input.lines().getOrNull(y)?.getOrNull(x)){
                    '.' -> Open(Point(x,y))
                    'A' -> Amber(Point(x,y))
                    'B' -> Bronze(Point(x,y))
                    'C' -> Copper(Point(x,y))
                    'D' -> Desert(Point(x,y))
                    else -> Wall(Point(x,y))
                }
                list.add(p)
            }
        }
        points = list
    }

    private fun get(point: Point) = points.find { it.point == point }

    fun possibleMoves(): List<Pair<String, Int>> {
        return points.filterIsInstance<Amphipod>().flatMap { amphipod ->
            val goTo = if(amphipod.inHallway()){
                nextInGoal(amphipod)?.let { listOf(it) } ?: listOf()
            }else{
                if(amphipod.inGoal() && allUnderIsValid(amphipod)) return@flatMap listOf()
                points.filter { it is Open && it.inHallway() }
            }.filter { canGoToPoint(amphipod, it) }
            goTo.map {
                val steps = abs(amphipod.point.x - it.point.x) + abs(amphipod.point.y - it.point.y)
                Pair(printSwap(amphipod, it), amphipod.energy * steps)
            }
        }
    }

    private fun canGoToPoint(amphipod: Amphipod, open: CavePoint): Boolean{
        if(open.inHallway() && listOf(3,5,7,9).contains(open.point.x)) return false
        for(x in minOf(amphipod.point.x, open.point.x)+1 until maxOf(amphipod.point.x, open.point.x)){
            if(get(Point(x, 1)) !is Open) return false
        }
        fun validY(x: Int, range: IntRange): Boolean{
            for(y in range){
                if(get(Point(x, y)) !is Open) return false
            }
            return true
        }
        return if(amphipod.inHallway()){
            validY(open.point.x, amphipod.point.y until open.point.y)
        }else{
            validY(amphipod.point.x, open.point.y until amphipod.point.y)
        }
    }

    private fun printSwap(p1: CavePoint, p2: CavePoint): String{
        var map = ""
        for(y in 0 until height){
            for(x in 0 until width){
                map += when(Point(x,y)){
                    p1.point -> p2.symbol
                    p2.point -> p1.symbol
                    else -> points.find { it.point.x == x && it.point.y == y }!!.symbol
                }
            }
            if(y != height - 1) map += "\n"
        }
        return map
    }

    fun heurestic(): Int{
        return points.filterIsInstance<Amphipod>().sumOf { amphipod ->
            val ySteps = if(amphipod.inHallway()) 2 else 5
            (abs(amphipod.goalX - amphipod.point.x) + ySteps) * amphipod.energy
        }
    }

    fun goalReached() = points.filterIsInstance<Amphipod>().all { it.inGoal() }

    private fun nextInGoal(amphipod: Amphipod): Open?{
        for(y in height-2 downTo height-1-goalDepth){
            val p = get(Point(amphipod.goalX, y))
            if(p is Amphipod && p.symbol != amphipod.symbol) return null
            if(p is Open) return p
        }
        return null
    }

    private fun allUnderIsValid(amphipod: Amphipod): Boolean{
        for(y in height-2 downTo amphipod.point.y){
            if(get(Point(amphipod.goalX, y))?.symbol != amphipod.symbol) return false
        }
        return true
    }
}

sealed class CavePoint(val symbol: Char, val point: Point){
    fun inHallway() = point.y == 1
}
sealed class Amphipod(symbol: Char, point: Point, val energy: Int, val goalX: Int): CavePoint(symbol, point){
    fun inGoal() = point.x == goalX
}

class Wall(point: Point): CavePoint('#', point)
class Open(point: Point): CavePoint('.', point)
class Amber(point: Point): Amphipod('A', point, 1, 3)
class Bronze(point: Point): Amphipod('B', point,10, 5)
class Copper(point: Point): Amphipod('C', point,100, 7)
class Desert(point: Point): Amphipod('D', point,1000, 9)

fun main(){
    val input = getText("day23.txt")
    val map = AmphipodAStar(input)
    val task1 = map.aStar()
    println(task1)
    val foldedMap = AmphipodAStar(withFoldInput(input))
    val task2 = foldedMap.aStar()
    println(task2)
}

fun withFoldInput(input: String): String{
    val lines = input.lines().toMutableList()
    lines.add(3, "  #D#C#B#A#")
    lines.add(4, "  #D#B#A#C#")
    return lines.joinToString("\n")
}