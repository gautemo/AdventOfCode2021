import shared.getText
import kotlin.math.max
import kotlin.math.min

fun highestPosTrajectory(input: String) = hittingProbes(input).maxOf { it.maxY }
fun nrHitTrajectories(input: String) = hittingProbes(input).size

private fun hittingProbes(input: String): List<Probe> {
    val target = findTarget(input)
    val onTarget = mutableListOf<Probe>()
    for(x in 0..300){
        for(y in -100..200){
            val probe = tryTrajectory(x, y, target)
            if(probe != null) onTarget.add(probe)
        }
    }
    return onTarget
}

private fun findTarget(input: String): Target{
    fun findRange(XorY: String): IntProgression {
        val a = Regex("""(?<=$XorY=)-?\d+""").find(input)!!.value.toInt()
        val b = Regex("""(?<=$XorY=-?\d\d?\d?\.\.)-?\d+""").find(input)!!.value.toInt()
        return if(a <= b) a..b else a downTo b
    }
    return Target(findRange("x"), findRange("y"))
}

private fun tryTrajectory(x: Int, y: Int, target: Target): Probe?{
    val probe = Probe(x, y)
    while (!probe.toFar(target)){
        probe.move()
        if(probe.onTarget(target)) return probe
    }
    return null
}

data class Target(val xRange: IntProgression, val yRange: IntProgression)

class Probe(var xVelocity: Int, var yVelocity: Int){
    private var x = 0
    private var y = 0
    var maxY = 0

    fun move(){
        x += xVelocity
        y += yVelocity
        maxY = max(y, maxY)
        when{
            xVelocity > 0 -> xVelocity--
            xVelocity < 0 -> xVelocity++
        }
        yVelocity--
    }

    fun toFar(target: Target): Boolean{
        return when{
            x < min(target.xRange.first, target.xRange.last) && xVelocity <= 0 -> true
            x > max(target.xRange.first, target.xRange.last) && xVelocity <= 0 -> true
            y < min(target.yRange.first, target.yRange.last) -> true
            else -> false
        }
    }

    fun onTarget(target: Target) = target.xRange.contains(x) && target.yRange.contains(y)
}

fun main(){
    val input = getText("day17.txt")
    val task1 = highestPosTrajectory(input)
    println(task1)
    val task2 = nrHitTrajectories(input)
    println(task2)
}