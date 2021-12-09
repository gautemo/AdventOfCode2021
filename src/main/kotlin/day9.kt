import shared.getLines

fun findRiskLevelsSum(input: List<String>): Int{
    val map = XYMap(input)
    return map.getLowPoints().sumOf { map.getValue(it)!! + 1 }
}

fun findLargestBasinsMultiplied(input: List<String>): Int{
    val map = XYMap(input)
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

private class XYMap(input: List<String>) {
    private val width = input.first().length
    private val height = input.size
    private val xyMap = mutableMapOf<Point, Int>()

    init {
        for((y, row) in input.withIndex()) {
            for ((x, char) in row.withIndex()) {
                xyMap[Point(x, y)] = char.digitToInt()
            }
        }
    }

    fun getValue(point: Point) = xyMap[point]

    private fun findAdjacentPoints(point: Point): List<Point>{
        val list = mutableListOf<Point>()
        val x = point.x
        val y = point.y
        if(xyMap[Point(x-1, y)] != null) list.add(Point(x-1, y))
        if(xyMap[Point(x, y-1)] != null) list.add(Point(x, y-1))
        if(xyMap[Point(x, y+1)] != null) list.add(Point(x, y+1))
        if(xyMap[Point(x+1, y)] != null) list.add(Point(x+1, y))
        return list
    }

    private fun lowPoint(point: Point) = findAdjacentPoints(point).all { xyMap[point]!! < xyMap[it]!! }

    fun getLowPoints(): List<Point>{
        val list = mutableListOf<Point>()
        for(y in 0 until height){
            for(x in 0 until width){
                val p = Point(x,y)
                if(lowPoint(p)) list.add(p)
            }
        }
        return list
    }

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

private data class Point(val x: Int, val y: Int)