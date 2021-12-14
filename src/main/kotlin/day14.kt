import shared.getText

fun polymerTemplate(input: String, times: Int = 10): Long{
    val (template, insertionsString) = input.split("\n\n")
    val insertions = insertionsString.lines().map {
        val (first, second) = it.split("->")
        Pair(first.trim(), second.trim())
    }
    var countMap = mutableMapOf<String, Long>()
    template.windowed(2).forEach {
        countMap[it] = countMap.getOrDefault (it, 0) + 1
    }
    repeat(times){
        val newCountMap = mutableMapOf<String, Long>()
        countMap.forEach { pair ->
            val match = insertions.find { it.first == pair.key }
            if(match != null) {
                val pair1 = "${pair.key.first()}${match.second}"
                val pair2 = "${match.second}${pair.key.last()}"
                newCountMap[pair1] = newCountMap.getOrDefault(pair1, 0) + pair.value
                newCountMap[pair2] = newCountMap.getOrDefault(pair2, 0) + pair.value
            }
        }
        countMap = newCountMap
    }
    val countLetters = mutableMapOf<Char, Long>()
    countMap.forEach {
        countLetters[it.key.first()] = countLetters.getOrDefault(it.key.first(), 0) + it.value
        countLetters[it.key.last()] = countLetters.getOrDefault(it.key.last(), 0) + it.value
    }
    countLetters.forEach{
        countLetters[it.key] = it.value / 2
    }
    countLetters[template.first()] = countLetters[template.first()]!! + 1
    countLetters[template.last()] = countLetters[template.last()]!! + 1
    val numbers =  countLetters.map { it.value }.sorted()
    return numbers.last() - numbers.first()
}

fun main(){
    val input = getText("day14.txt")
    val task1 = polymerTemplate(input)
    println(task1)
    val task2 = polymerTemplate(input, 40)
    println(task2)
}