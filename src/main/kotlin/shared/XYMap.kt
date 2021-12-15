package shared

open class XYMap<T>(input: List<String>, toType: (char: Char) -> T) {
    val width = input.first().length
    val height = input.size
    private val xyMap = mutableMapOf<Point, T>()

    init {
        for((y, row) in input.withIndex()) {
            for ((x, char) in row.withIndex()) {
                xyMap[Point(x, y)] = toType(char)
            }
        }
    }

    fun getValue(point: Point) = xyMap[point]!!
    fun setValue(point: Point, value: T) {
        xyMap[point] = value
    }

    fun allPoints() = xyMap.map { it.key }

    fun findAdjacentPoints(point: Point, includeDiagonal: Boolean = false): List<Point>{
        val list = mutableListOf<Point>()
        val x = point.x
        val y = point.y
        if(includeDiagonal && xyMap[Point(x-1, y-1)] != null) list.add(Point(x-1, y-1))
        if(xyMap[Point(x-1, y)] != null) list.add(Point(x-1, y))
        if(includeDiagonal && xyMap[Point(x-1, y+1)] != null) list.add(Point(x-1, y+1))
        if(xyMap[Point(x, y-1)] != null) list.add(Point(x, y-1))
        if(xyMap[Point(x, y+1)] != null) list.add(Point(x, y+1))
        if(includeDiagonal && xyMap[Point(x+1, y-1)] != null) list.add(Point(x+1, y-1))
        if(xyMap[Point(x+1, y)] != null) list.add(Point(x+1, y))
        if(includeDiagonal && xyMap[Point(x+1, y+1)] != null) list.add(Point(x+1, y+1))
        return list
    }
}