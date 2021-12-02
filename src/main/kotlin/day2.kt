import shared.getLines

data class Location(var horizontalPos: Int = 0, var depth: Int = 0)

fun moveProduces(instructions: List<String>): Int{
    val location = Location()
    for(instruction in instructions){
        val (command, units) = instruction.split(" ")
        when(command){
            "forward" -> location.horizontalPos += units.toInt()
            "down" -> location.depth += units.toInt()
            "up" -> location.depth -= units.toInt()
        }
    }
    return location.horizontalPos * location.depth
}

fun main(){
    val input = getLines("day2.txt")
    val part1 = moveProduces(input)
    println(part1)
}