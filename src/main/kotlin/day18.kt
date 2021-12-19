import shared.getLines
import kotlin.math.ceil

fun snailHomework(assignment: List<String>): Int{
    var total = toSnailElement(assignment.first())
    for(line in assignment.drop(1)){
        total = SnailPair(total, toSnailElement(line))
        do {
            val exploded = explode(total)
            var reduced = false
            if(!exploded){
                reduced = reduce(total)
            }
        }while (exploded || reduced)
    }
    return toMagnitude(total as SnailPair)
}

sealed class SnailElement{}
class SnailValue(var value: Int) : SnailElement()
class SnailPair(var left: SnailElement, var right: SnailElement) : SnailElement()

private fun toSnailElement(line: String): SnailElement{
    if(!line.contains(',')) return SnailValue(line.toInt())
    val l = line.substring(1, line.length-1)
    val left = l.take(l.rootComma())
    val right = l.drop(l.rootComma()+1)
    return SnailPair(toSnailElement(left), toSnailElement(right))
}

private fun String.rootComma(): Int{
    for((i,c) in this.withIndex()){
        if(c == ',' && this.take(i).count { it == '[' } == this.take(i).count { it == ']' }){
            return i
        }
    }
    throw Exception("should have root ,")
}

fun explode(snailPair: SnailPair, rootPair: SnailPair = snailPair, nested: Int = 0): Boolean {
    if(nested == 4){
        val list = snailValueLine(rootPair)
        val explodeIndex = list.indexOf(snailPair.left)
        if(explodeIndex-1 >= 0) list[explodeIndex-1].value += (snailPair.left as SnailValue).value
        if(explodeIndex+2 < list.size) list[explodeIndex+2].value += (snailPair.right as SnailValue).value
        return true
    }
    if(snailPair.left is SnailPair){
        val exploded = explode(snailPair.left as SnailPair, rootPair, nested + 1)
        if(exploded) {
            if(nested == 3) snailPair.left = SnailValue(0)
            return exploded
        }
    }
    if(snailPair.right is SnailPair){
        val exploded = explode(snailPair.right as SnailPair, rootPair,  nested + 1)
        if(exploded) {
            if(nested == 3) snailPair.right = SnailValue(0)
            return exploded
        }
    }
    return false
}

fun reduce(snailPair: SnailPair): Boolean{
    if(snailPair.left is SnailValue && (snailPair.left as SnailValue).value >= 10){
        val value = (snailPair.left as SnailValue).value
        snailPair.left = SnailPair(SnailValue(value / 2), SnailValue(ceil(value.toDouble() / 2).toInt()))
        return true
    }
    if(snailPair.left is SnailPair && reduce(snailPair.left as SnailPair)) return true
    if(snailPair.right is SnailValue && (snailPair.right as SnailValue).value >= 10){
        val value = (snailPair.right as SnailValue).value
        snailPair.right = SnailPair(SnailValue(value / 2), SnailValue(ceil(value.toDouble() / 2).toInt()))
        return true
    }
    if(snailPair.right is SnailPair && reduce(snailPair.right as SnailPair)) return true
    return false
}

private fun snailValueLine(snailPair: SnailPair): List<SnailValue>{
    val list = mutableListOf<SnailValue>()
    if(snailPair.left is SnailValue){
        list.add(snailPair.left as SnailValue)
    }else{
        list.addAll(snailValueLine(snailPair.left as SnailPair))
    }
    if(snailPair.right is SnailValue){
        list.add(snailPair.right as SnailValue)
    }else{
        list.addAll(snailValueLine(snailPair.right as SnailPair))
    }
    return list
}

private fun toMagnitude(snailPair: SnailPair): Int{
    val left = if(snailPair.left is SnailValue) (snailPair.left as SnailValue).value else toMagnitude(snailPair.left as SnailPair)
    val right = if(snailPair.right is SnailValue) (snailPair.right as SnailValue).value else toMagnitude(snailPair.right as SnailPair)
    return 3*left + 2*right
}

fun largestMagnitudeSnailHomeWork(assignment: List<String>): Int{
    var max = 0
    for(line1 in assignment){
        for(line2 in assignment){
            if(line1 != line2){
                val magnitude = snailHomework(listOf(line1, line2))
                if(magnitude > max) max = magnitude
            }
        }
    }
    return max
}

fun main(){
    val input = getLines("day18.txt")
    val task1 = snailHomework(input)
    println(task1)
    val task2 = largestMagnitudeSnailHomeWork(input)
    println(task2)
}
