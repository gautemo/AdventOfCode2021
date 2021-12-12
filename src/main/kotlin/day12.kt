import shared.getLines

fun String.isUppercase() = this.uppercase() == this

class CaveNavigate(lines: List<String>, private val allowTwice: Boolean = false) {
    private val caveNodes = mutableListOf<CaveNode>()
    private var options = mutableListOf<List<CaveNode>>()
    private val reachedEnd = mutableListOf<List<CaveNode>>()

    init {
        for (line in lines) {
            val (a, b) = line.split("-")
            val existingA = caveNodes.find { it.name == a }
            if (existingA == null) {
                caveNodes.add(CaveNode(a, mutableListOf(b)))
            } else {
                existingA.connected.add(b)
            }
            val existingB = caveNodes.find { it.name == b }
            if (existingB == null) {
                caveNodes.add(CaveNode(b, mutableListOf(a)))
            } else {
                existingB.connected.add(a)
            }
        }
        options.add(listOf(caveNodes.find { it.name == "start" }!!))
    }

    fun findDistinctPaths(): Int {
        while (!searchCave()) {
            reachedEnd.addAll(options.filter { it.last().name == "end" })
        }
        return reachedEnd.size
    }

    private fun canVisitCave(visited: List<CaveNode>, name: String): Boolean {
        if (name.isUppercase()) return true
        if (name == "start") return false
        if (allowTwice) {
            val usedUp = visited
                .map { it.name }
                .filter { !it.isUppercase() }
                .groupingBy { it }
                .eachCount()
                .any { it.value == 2 }
            if (!usedUp) return true
        }
        return visited.none { it.name == name }
    }

    private fun searchCave(): Boolean {
        val newOptions = mutableListOf<List<CaveNode>>()
        var isDone = true
        for (option in options.filter { it.last().name != "end" }) {
            val isAt = option.last()
            val nextCaves = caveNodes
                .filter { isAt.connected.contains(it.name) }
                .filter { canVisitCave(option, it.name) }
            nextCaves.forEach {
                isDone = false
                newOptions.add(option + it)
            }
        }
        if (!isDone) {
            options = newOptions
        }
        return isDone
    }
}

data class CaveNode(val name: String, val connected: MutableList<String>)

fun main(){
    val input = getLines("day12.txt")
    val caveTask1 = CaveNavigate(input)
    println(caveTask1.findDistinctPaths())
    val caveTask2 = CaveNavigate(input, true)
    println(caveTask2.findDistinctPaths())
}