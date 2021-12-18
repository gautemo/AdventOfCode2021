import kotlin.math.ceil

fun snailHomework(assignment: List<String>): Int{
    var total = toSnailElement(assignment.first())
    for(line in assignment.drop(1)){
        total = SnailPair(total, toSnailElement(line))
        do {
            val exploded = explode(total)
            var reduced = false
            if(exploded != null){
                reduced = reduce(total)
            }
        }while (exploded != null || reduced)

    }
    return 0
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

fun explode(snailPair: SnailPair, nested: Int = 0): Explosion? {
    if(nested == 4){
        return Explosion((snailPair.left as SnailValue).value, (snailPair.right as SnailValue).value)
    }
    if(snailPair.left is SnailPair){
        val exploded = explode(snailPair.left as SnailPair, nested + 1)
        if(exploded != null) {
            if(!exploded.updatedPair){
                snailPair.left = SnailValue(0)
                exploded.updatedPair = true
            }
            if(snailPair.right is SnailValue) {
                    (snailPair.right as SnailValue).value += exploded.updateRight
                exploded.updateRight = 0
            }
            return exploded
        }
    }
    if(snailPair.right is SnailPair){
        val exploded = explode(snailPair.right as SnailPair, nested + 1)
        if(exploded != null) {
            if(!exploded.updatedPair){
                snailPair.right = SnailValue(0)
                exploded.updatedPair = true
            }
            if(snailPair.left is SnailValue) {
                (snailPair.left as SnailValue).value += exploded.updateLeft
                exploded.updateLeft = 0
            }
            return exploded
        }
    }
    return null
}

fun reduce(snailPair: SnailPair): Boolean{
    if(snailPair.left is SnailValue && (snailPair.left as SnailValue).value >= 10){
        val value = (snailPair.left as SnailValue).value
        snailPair.left = SnailPair(SnailValue(value / 2), SnailValue(ceil(value.toDouble() / 2).toInt()))
        return true
    }
    if(snailPair.right is SnailValue && (snailPair.right as SnailValue).value >= 10){
        val value = (snailPair.right as SnailValue).value
        snailPair.right = SnailPair(SnailValue(value / 2), SnailValue(ceil(value.toDouble() / 2).toInt()))
        return true
    }
    if(snailPair.left is SnailPair) return reduce(snailPair.left as SnailPair)
    if(snailPair.right is SnailPair) return reduce(snailPair.right as SnailPair)
    return false
}

data class Explosion(var updateLeft: Int, var updateRight: Int, var updatedPair: Boolean = false)

fun main(){
    val input = """
        [[[[4,3],4],4],[7,[[8,4],9]]]
        [1,1]
    """.trimIndent().lines()
    snailHomework(input)
}