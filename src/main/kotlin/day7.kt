import shared.getText
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.abs
import kotlin.math.pow

fun alignCrabs(input: String, nthTriangularNumber: Boolean = false): Int{
    val crabs = input.split(",").map { it.toInt() }
    val startCheck = crabs.minOf { it }
    val stopCheck = crabs.maxOf { it }
    var lowestDist = MAX_VALUE
    for(i in startCheck..stopCheck){
        val dist = crabs.distTo(i, nthTriangularNumber)
        if(dist < lowestDist) lowestDist = dist
    }
    return lowestDist
}

fun List<Int>.distTo(check: Int, nthTriangularNumber: Boolean = false) = this.sumOf {
    val dist = abs(it - check)
    if(nthTriangularNumber) nthTriangularNumber(dist) else dist
}

fun nthTriangularNumber(n: Int) = ((n.toDouble().pow(2) + n) / 2).toInt()

fun main(){
    val input = getText("day7.txt")
    val task1 = alignCrabs(input)
    println(task1)
    val task2 = alignCrabs(input, true)
    println(task2)
}