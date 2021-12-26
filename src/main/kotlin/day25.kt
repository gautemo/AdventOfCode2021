import shared.Point
import shared.XYMap
import shared.getText

fun cucumbersStopsAt(input: String): Int{
    val bottom = CucumbersMap(input)
    return bottom.waitForCucumbersToStop()
}

open class CucumberSpace
data class Cucumber(val eastDir: Boolean) : CucumberSpace(){
    fun next(current: Point, width: Int, height: Int): Point {
        return if(eastDir){
            val x = if(current.x+1 == width) 0 else current.x+1
            Point(x, current.y)
        }else{
            val y = if(current.y+1 == height) 0 else current.y+1
            Point(current.x, y)
        }
    }
}

class CucumbersMap(input: String): XYMap<CucumberSpace>(input.lines(), { c: Char -> if(c == '.') CucumberSpace() else Cucumber(c == '>') }) {
    private fun step(): Boolean{
        val movedEast = moveCucumbers(true)
        val movedSouth = moveCucumbers(false)
        return movedEast || movedSouth
    }

    private fun moveCucumbers(east: Boolean): Boolean{
        val movingCucumbers = allPoints().map{ Pair(it, getValue(it)) }.filter {
            if(it.second !is Cucumber || (it.second as Cucumber).eastDir != east) return@filter false
            val canMove = getValue((it.second as Cucumber).next(it.first, width, height)) !is Cucumber
            canMove
        }
        if(movingCucumbers.isNotEmpty()){
            movingCucumbers.filterIsInstance<Pair<Point,Cucumber>>().forEach {
                setValue(it.first, CucumberSpace())
                setValue(it.second.next(it.first, width, height), it.second)
            }
            return true
        }
        return false
    }

    fun waitForCucumbersToStop(): Int{
        var steps = 1
        while(step()){
            steps++
        }
        return steps
    }
}

fun main(){
    val input = getText("day25.txt")
    val result = cucumbersStopsAt(input)
    println(result)
}