import shared.getLines

data class Cube(val on: Boolean, var x: Range, var y: Range, var z: Range){
    fun volume() = x.length() * y.length() * z.length()
}
data class Range(val from: Int, val to: Int){
    fun length() = (to - from).toLong() + 1
    fun valid() = from <= to
    fun intersect(b: Range) = Range(maxOf(from, b.from), minOf(to, b.to))
}

class AllCubes(private var cubes: List<Cube>, allowedSpace: Range? = null){
    init {
        if(allowedSpace != null){
            cubes = cubes.map {
                val x = allowedSpace.intersect(it.x)
                val y = allowedSpace.intersect(it.y)
                val z = allowedSpace.intersect(it.z)
                if(x.valid() && y.valid() && z.valid()){
                    return@map Cube(it.on, x, y, z)
                }
                null
            }.filterNotNull()
        }
    }
    fun volume(): Long{
        var sum = 0L
        for((i, cube) in cubes.withIndex()){
            if(!cube.on) continue
            var volume = cube.volume()
            val ignore = cubes.drop(i+1).map {
                val intersectX = cube.x.intersect(it.x)
                val intersectY = cube.y.intersect(it.y)
                val intersectZ = cube.z.intersect(it.z)
                if(intersectX.valid() && intersectY.valid() && intersectZ.valid()){
                    return@map Cube(true, intersectX, intersectY, intersectZ)
                }
                null
            }.filterNotNull()
            val ignoreAllCubes = AllCubes(ignore)
            volume -= ignoreAllCubes.volume()
            sum += volume
        }
        return sum
    }

    companion object{
        fun initialize(input: List<String>, allowedSpace: Range? = null): AllCubes{
            val cubes = input.map {
                val nrs = Regex("""-?\d+""").findAll(it)
                Cube(
                    it.contains("on"),
                    Range(nrs.elementAt(0).value.toInt(), nrs.elementAt(1).value.toInt()),
                    Range(nrs.elementAt(2).value.toInt(), nrs.elementAt(3).value.toInt()),
                    Range(nrs.elementAt(4).value.toInt(), nrs.elementAt(5).value.toInt()),
                )
            }
            return AllCubes(cubes, allowedSpace)
        }
    }
}

fun main(){
    val input = getLines("day22.txt")
    val allCubesLimited = AllCubes.initialize(input, Range(-50,50))
    val task1 = allCubesLimited.volume()
    println(task1)
    val allCubes = AllCubes.initialize(input)
    val task2 = allCubes.volume()
    println(task2)
}