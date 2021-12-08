import shared.getLines

fun findNumbers(input: List<String>): Int{
    var one = 0
    var four = 0
    var seven = 0
    var eight = 0
    for(line in input){
        val output = line.split("|").last().trim().split(" ")
        one += output.count { it.length == 2 }
        four += output.count { it.length == 4 }
        seven += output.count { it.length == 3 }
        eight += output.count { it.length == 7 }
    }
    return one + four + seven + eight
}

fun findOutputSum(input: List<String>): Int{
    var sum = 0
    for (line in input){
        val (patterns, output) = line.split("|").map { it.trim().split(" ") }
        sum += outputValue(patterns, output)
    }
    return sum
}

fun outputValue(patterns: List<String>, output: List<String>): Int{
    val map = mutableMapOf(
        Pair('a', mutableListOf('a','b','c','d','e','f','g')),
        Pair('b', mutableListOf('a','b','c','d','e','f','g')),
        Pair('c', mutableListOf('a','b','c','d','e','f','g')),
        Pair('d', mutableListOf('a','b','c','d','e','f','g')),
        Pair('e', mutableListOf('a','b','c','d','e','f','g')),
        Pair('f', mutableListOf('a','b','c','d','e','f','g')),
        Pair('g', mutableListOf('a','b','c','d','e','f','g')),
    )
    while(!map.values.all { it.size == 1 }){
        val foundOne = map['c']!!.size <= 2 && map['f']!!.size <= 2
        for(pattern in patterns){
            when{
                pattern.length == 2 -> {
                    map['c']!!.removeIf { possibility -> pattern.none { it == possibility } }
                    map['f']!!.removeIf { possibility -> pattern.none { it == possibility } }
                }
                pattern.length == 3 && foundOne -> {
                    val a = pattern.filterNot { map['c']!!.contains(it) || map['f']!!.contains(it) }
                    map['a']!!.removeIf { !a.contains(it) }
                }
                pattern.length == 4 && map['d']!!.size == 1 && foundOne -> {
                    val b = pattern.filterNot { map['d']!!.contains(it) || map['c']!!.contains(it) || map['f']!!.contains(it) }
                    map['b']!!.removeIf { !b.contains(it) }
                }
                pattern.length == 4 -> {
                    map['b']!!.removeIf { possibility -> pattern.none { it == possibility } }
                    map['c']!!.removeIf { possibility -> pattern.none { it == possibility } }
                    map['d']!!.removeIf { possibility -> pattern.none { it == possibility } }
                    map['f']!!.removeIf { possibility -> pattern.none { it == possibility } }
                }
                pattern.length == 5 && foundOne -> {
                    if(map['a']!!.size == 1){
                        val dg = pattern.filterNot { map['a']!!.contains(it) || map['c']!!.contains(it) || map['f']!!.contains(it) }
                        map['d']!!.removeIf { !dg.contains(it) }
                        map['g']!!.removeIf { !dg.contains(it) }
                    }
                    if(map['b']!!.size == 1 && pattern.contains(map['b']!!.first())){
                        map['f']!!.removeIf { !pattern.contains(it) }
                    }
                }
            }
        }
        for(option in map){
            if(option.value.size == 1){
                map.filter { it.key != option.key }.forEach { (_, value) -> value.removeIf { it == option.value.first() } }
            }
        }
    }
    return output.map {
        val a = it.contains(map['a']!!.first())
        val b = it.contains(map['b']!!.first())
        val c = it.contains(map['c']!!.first())
        val d = it.contains(map['d']!!.first())
        val e = it.contains(map['e']!!.first())
        val f = it.contains(map['f']!!.first())
        val g = it.contains(map['g']!!.first())
        if(a && b && c && !d && e && f && g) return@map '0'
        if(!a && !b && c && !d && !e && f && !g) return@map '1'
        if(a && !b && c && d && e && !f && g) return@map '2'
        if(a && !b && c && d && !e && f && g) return@map '3'
        if(!a && b && c && d && !e && f && !g) return@map '4'
        if(a && b && !c && d && !e && f && g) return@map '5'
        if(a && b && !c && d && e && f && g) return@map '6'
        if(a && !b && c && !d && !e && f && !g) return@map '7'
        if(a && b && c && d && e && f && g) return@map '8'
        '9'
    }.joinToString("").toInt()
}

fun main(){
    val input = getLines("day8.txt")
    val task1 = findNumbers(input)
    println(task1)
    val task2 = findOutputSum(input)
    println(task2)
}