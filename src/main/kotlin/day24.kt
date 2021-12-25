import shared.getLines

class Alu(private val monad: List<String>){
    private val variables = mutableMapOf(
        Pair('w', 0),
        Pair('x', 0),
        Pair('y', 0),
        Pair('z', 0),
    )

    fun checkModelNumber(modelNumber: String): Boolean{
        variables.keys.forEach { variables[it] = 0 }
        var readIndex = 0
        try{
            for(instruction in monad){
                val command = instruction.split(" ")[0]
                val a = instruction.split(" ")[1].first()
                val b = instruction.split(" ").getOrNull(2)
                variables[a] = when(command){
                    "inp" -> {
                        val value = modelNumber[readIndex].digitToInt()
                        readIndex++
                        value
                    }
                    "add" -> variables[a]!! + getBValue(b!!)
                    "mul" -> variables[a]!! * getBValue(b!!)
                    "div" -> variables[a]!! / getBValue(b!!)
                    "mod" -> variables[a]!! % getBValue(b!!)
                    "eql" -> if(variables[a]!! == getBValue(b!!)) 1 else 0
                    else -> throw Exception("Should always find")
                }
            }
            //println(variables['z'])
            return variables['z'] == 0
        }catch(e: Exception) {
            return false
        }
    }

    private fun getBValue(b: String): Int {
        return try{
            b.toInt()
        }catch (e: Exception){
            variables[b.first()]!!
        }
    }
}

fun main(){
    val input = getLines("day24.txt")
    val alu = Alu(input)

    /*
    inp w
    mul x 0
    add x z
    mod x 26
    div z {DIV}
    add x {CHECK}
    eql x w
    eql x 0
    mul y 0
    add y 25
    mul y x
    add y 1
    mul z y
    mul y 0
    add y w
    add y {OFFSET}
    mul y x
    add z y
     */

    val relations = mutableListOf<Relation>()

    val stack = mutableListOf<StackElement>()
    getSections(input).forEachIndexed { index, section ->
        if(section.div == 1){
            stack.add(StackElement(index, section.offset))
        }else{
            val popped = stack.removeLast()
            println("model_nr[$index] == model_nr[${popped.nr}] + ${popped.value + section.check}")
            relations.add(Relation(index, popped.nr, popped.value + section.check))
        }
    }

    val max = getModelNumber(relations, 9 downTo 1)
    val maxValid = alu.checkModelNumber(max)
    println("max model number $max is $maxValid")
    val min = getModelNumber(relations, 1..9)
    val minValid = alu.checkModelNumber(min)
    println("min model number $min is $minValid")
}

data class Section(val div: Int, val check: Int, val offset: Int)
data class StackElement(val nr: Int, val value: Int)
data class Relation(val nrA: Int, val nrB: Int, val diff: Int)

fun getSections(input: List<String>): List<Section>{
    return input.chunked(18).map {
        val div = it[4].split(" ")[2].toInt()
        val check = it[5].split(" ")[2].toInt()
        val offset = it[15].split(" ")[2].toInt()
        Section(div, check, offset)
    }
}

fun getModelNumber(relations: List<Relation>, intProgression: IntProgression): String{
    var modelNumber = ""
    for(i in 0 until 14){
        val pair = relations.find { it.nrA == i || it.nrB == i }!!
        val chose = if(pair.nrA == i){
            intProgression.toList().first { IntRange(1,9).contains(it - pair.diff) }
        }else{
            intProgression.toList().first { IntRange(1,9).contains(it + pair.diff) }
        }
        modelNumber += chose
    }
    return modelNumber
}