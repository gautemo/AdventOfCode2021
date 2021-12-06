import shared.getText

fun simulateLanternfishGrowth(input: String, times: Int = 80): Long{
    val map = mutableMapOf(
        Pair(0, input.count { it == '0' }.toLong()),
        Pair(1, input.count { it == '1' }.toLong()),
        Pair(2, input.count { it == '2' }.toLong()),
        Pair(3, input.count { it == '3' }.toLong()),
        Pair(4, input.count { it == '4' }.toLong()),
        Pair(5, input.count { it == '5' }.toLong()),
        Pair(6, input.count { it == '6' }.toLong()),
        Pair(7, input.count { it == '7' }.toLong()),
        Pair(8, input.count { it == '8' }.toLong()),
    )
    for(i in 1..times){
        val zeros = map[0]!!
        map[0] = map[1]!!
        map[1] = map[2]!!
        map[2] = map[3]!!
        map[3] = map[4]!!
        map[4] = map[5]!!
        map[5] = map[6]!!
        map[6] = map[7]!! + zeros
        map[7] = map[8]!!
        map[8] = zeros
    }
    return map.values.sumOf { it }
}

fun main(){
    val input = getText("day6.txt").trim()
    val task1 = simulateLanternfishGrowth(input)
    println(task1)
    val task2 = simulateLanternfishGrowth(input, 256)
    println(task2)
}