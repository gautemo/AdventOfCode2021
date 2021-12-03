import shared.getLines

fun main() {
    val input = getLines("day1.txt").map { it.toInt() }
    val result = countIncrease(input)
    println(result)

    val result2 = countIncrease(toWindows(input))
    println(result2)
}

fun countIncrease(seaLevels: List<Int>): Int{
    var last = seaLevels.first()
    var count = 0
    for (level in seaLevels){
        if(level > last) count++
        last = level
    }
    return count
}

fun toWindows(seaLevels: List<Int>): List<Int>{
    return seaLevels.mapIndexed { i, level ->
        val window = if(seaLevels.size > i + 2) level + seaLevels[i+1] + seaLevels[i+2] else -1
        window
    }.filter { it != -1 }
}