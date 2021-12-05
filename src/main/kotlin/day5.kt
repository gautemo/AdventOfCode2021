import shared.getLines

fun findOverlappingVents(lines: List<String>, includeDiagonals: Boolean = false): Int{
    val vents = lines.map { Vent(it) }.filter { includeDiagonals || !it.isDiagonal }
    val maxX = vents.maxOf { maxOf(it.xLine.first, it.xLine.last) }
    val maxY = vents.maxOf { maxOf(it.yLine.first, it.yLine.last) }
    val minX = vents.minOf { minOf(it.xLine.first, it.xLine.last) }
    val minY = vents.minOf { minOf(it.yLine.first, it.yLine.last) }
    var count = 0
    for(x in minX..maxX){
        for(y in minY..maxY){
            if(vents.countTo(2){ it.overPoint(x,y)}) count++
        }
    }
    return count
}

fun List<Vent>.countTo(goal: Int, predicate: (Vent) -> Boolean): Boolean{
    var count = 0
    for(vent in this){
        if(predicate(vent)) {
            count++
            if(count == goal) return true
        }
    }
    return false
}

class Vent(line: String){
    val xLine: IntProgression
    val yLine: IntProgression
    val isDiagonal: Boolean

    init {
        val (a, b) = line.split("->").map { coordinates -> coordinates.trim().split(",").map { it.toInt() } }
        xLine = if(a[0] <= b[0]) a[0]..b[0] else a[0] downTo b[0]
        yLine = if(a[1] <= b[1]) a[1]..b[1] else a[1] downTo b[1]
        isDiagonal = xLine.count() > 1 && yLine.count() > 1
    }

    fun overPoint(x: Int, y: Int): Boolean{
        if(isDiagonal){
            val indexX = xLine.indexOf(x)
            return indexX != -1 && indexX == yLine.indexOf(y)
        }
        return xLine.contains(x) && yLine.contains(y)
    }
}

fun main(){
    val input = getLines("day5.txt")
    val result = findOverlappingVents(input)
    println(result)
    val result2 = findOverlappingVents(input, true)
    println(result2)
}