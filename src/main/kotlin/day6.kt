import shared.getText
import kotlin.system.measureTimeMillis

fun simulateLanternfishGrowthTask1(input: String, times: Int = 80): Int{
    val list = input.split(",").map { LanternFish(it.toInt()) }.toMutableList()
    for(i in 1..times){
        val births = list.count { it.decrease() }
        list.addAll(List(births){ LanternFish() })
    }
    return list.size
}

fun simulateLanternfishGrowth(input: String, times: Int = 80): Long{
    val sets = mutableListOf(input.replace(",", ""))
    for(i in 1..times){
        for(j in sets.indices){
            sets[j] = oneTime2(sets[j])
        }
        sets.map { it }.forEach {
            if(it.length > 10000000){
                sets.remove(it)
                sets.addAll(it.chunked((it.length+1)/2))
            }
        }
        println(i)
        if(i > times - 6) println(sets.sumOf { it.length.toLong() })
    }
    return sets.sumOf { it.length.toLong() }
}

private fun oneTime(input: String): String{
    val list = input.map { LanternFish(it.digitToInt()) }.toMutableList()
    val births = list.count { it.decrease() }
    list.addAll(List(births){ LanternFish() })
    return list.joinToString("") { it.timer.toString() }
}

private fun oneTime2(input: String): String{
    return input.map {
        if(it == '0') {
            "68"
        }else{
            it.digitToInt() - 1
        }
    }.joinToString("")
}

class LanternFish(){
    var timer = 8

    constructor(timer: Int) : this() {
        this.timer = timer
    }

    fun decrease(): Boolean{
        timer--
        if(timer == -1) {
            timer = 6
            return true
        }
        return false
    }
}

fun main(){
    val input = getText("day6.txt").trim()
    //val result = simulateLanternfishGrowth(input)
    //println(result)
    simulateLanternfishGrowth("1", 256)
}