import shared.getLines

data class Location(var horizontalPos: Int = 0, var depth: Int = 0, var aim: Int = 0)

fun moveProduces(instructions: List<String>, action: (command: String, units: Int, location: Location) -> Unit): Int{
    val location = Location()
    for(instruction in instructions){
        val (command, units) = instruction.split(" ")
        action(command, units.toInt(), location)
    }
    return location.horizontalPos * location.depth
}

private fun commandActionPart1(command: String, units: Int, location: Location){
    when(command){
        "forward" -> location.horizontalPos += units
        "down" -> location.depth += units
        "up" -> location.depth -= units
    }
}

private fun commandActionPart2(command: String, units: Int, location: Location){
    when(command){
        "forward" -> {
            location.horizontalPos += units
            location.depth += location.aim * units
        }
        "down" -> location.aim += units
        "up" -> location.aim -= units
    }
}

fun movePart1(instructions: List<String>) = moveProduces(instructions, ::commandActionPart1)
fun movePart2(instructions: List<String>) = moveProduces(instructions, ::commandActionPart2)

fun main(){
    val input = getLines("day2.txt")
    val part1 = movePart1(input)
    println(part1)
    val part2 = movePart2(input)
    println(part2)
}