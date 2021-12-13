import shared.Point
import shared.getText

class Origami(input: String){
    private val xyMap = mutableMapOf<Point, Boolean>()
    private val folds: MutableList<String>
    init {
        val (dots, foldLines) = input.split("\n\n")
        folds = foldLines.lines().toMutableList()
        for(dot in dots.lines()){
            val (x,y) = dot.split(",")
            xyMap[Point(x.toInt(), y.toInt())] = true
        }
    }


    fun fold(){
        val fold = folds.removeFirst()
        val line = fold.split("=")[1].toInt()
        if(fold.contains("y")){
            for(dot in xyMap.filter { it.key.y > line }){
                val toY = line - (dot.key.y - line)
                if(toY >= 0) xyMap[Point(dot.key.x, toY)] = true
                xyMap.remove(dot.key)
            }
        }else{
            for(dot in xyMap.filter { it.key.x > line }){
                val toX = line - (dot.key.x - line)
                if(toX >= 0) xyMap[Point(toX, dot.key.y)] = true
                xyMap.remove(dot.key)
            }
        }
    }

    fun foldAll(){
        while(folds.size > 0) fold()
    }

    fun dots(): Int{
        return xyMap.size
    }

    fun print(){
        val maxX = xyMap.map { it.key.x }.maxOrNull() ?: 0
        val maxY = xyMap.map { it.key.y }.maxOrNull() ?: 0
        repeat(maxY + 1){ y->
            repeat(maxX + 1){ x->
                print(if(xyMap[Point(x,y)] == true) "# " else ". ")
            }
            println()
        }
    }
}

fun main(){
    val input = getText("day13.txt")
    val origami = Origami(input)
    origami.fold()
    val task1 = origami.dots()
    println(task1)
    origami.foldAll()
    origami.print()
}