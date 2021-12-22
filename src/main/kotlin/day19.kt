import shared.getText
import kotlin.math.abs

val seenFromScanner0 = mutableSetOf<Point3D>()
val scannerLocationsFromScanner0 = mutableSetOf<Point3D>()

fun findBeacons(input: String, minimumOverlap: Int = 12): Int{
    seenFromScanner0.clear()
    scannerLocationsFromScanner0.clear()
    val scanners = input.split("\n\n").map { Scanner(it.lines()) }
    seenFromScanner0.addAll(scanners.first().points)
    scannerLocationsFromScanner0.add(Point3D(0,0,0))
    do{
        for(scanner in scanners.filter { it.id != 0 }){
            matchBeacons(scanner, minimumOverlap)
        }
    } while(scannerLocationsFromScanner0.size != scanners.size)
    return seenFromScanner0.size
}

private fun matchBeacons(scanner: Scanner, minimumOverlap: Int) {
    for(referencePoint1 in seenFromScanner0){
        val rest1 = seenFromScanner0.filter { it != referencePoint1 }
        for(possiblePoints in scanner.flipFlops()){
            for(referencePoint2 in possiblePoints){
                val matches = possiblePoints.filter { it != referencePoint2 }.count { hasDistPoint(referencePoint1, rest1, referencePoint2, it) } + 1
                if(matches >= minimumOverlap){
                    val diffX = referencePoint1.x - referencePoint2.x
                    val diffY = referencePoint1.y - referencePoint2.y
                    val diffZ = referencePoint1.z - referencePoint2.z
                    seenFromScanner0.addAll(possiblePoints.map { Point3D(it.x + diffX, it.y + diffY, it.z + diffZ) })
                    scannerLocationsFromScanner0.add(Point3D(referencePoint1.x-referencePoint2.x,referencePoint1.y-referencePoint2.y,referencePoint1.z-referencePoint2.z))
                    return
                }
            }
        }
    }
}

private fun hasDistPoint(reference1: Point3D, rest1: List<Point3D>, reference2: Point3D, check: Point3D): Boolean{
    val checkXDist = reference2.x - check.x
    val checkYDist = reference2.y - check.y
    val checkZDist = reference2.z - check.z
    return rest1.any {
        val xDist = reference1.x - it.x
        val yDist = reference1.y - it.y
        val zDist = reference1.z - it.z
        xDist == checkXDist && yDist == checkYDist && zDist == checkZDist
    }
}

data class Point3D(val x: Int, val y: Int, val z: Int)

class Scanner(input: List<String>){
    val id = Regex("""\d+""").find(input.first())!!.value.toInt()
    val points: List<Point3D> = input.drop(1).map { beacon ->
        val points = beacon.split(',')
        Point3D(points[0].toInt(), points[1].toInt(), points[2].toInt())
    }

    fun flipFlops(): List<List<Point3D>>{
        return listOf(
            points.map { Point3D(it.x, it.y, it.z) }, // 1
            points.map { Point3D(-it.y, it.x, it.z) }, // 2
            points.map { Point3D(-it.x, -it.y, it.z) }, // 3
            points.map { Point3D(it.y, -it.x, it.z) }, // 4
            points.map { Point3D(-it.z, it.y, it.x) }, // 5
            points.map { Point3D(-it.y, -it.z, it.x) }, // 6
            points.map { Point3D(it.z, -it.y, it.x) }, // 7
            points.map { Point3D(it.y, it.z, it.x) }, // 8
            points.map { Point3D(-it.x, it.z, it.y) }, // 9
            points.map { Point3D(-it.z, -it.x, it.y) }, // 10
            points.map { Point3D(it.x, -it.z, it.y) }, // 11
            points.map { Point3D(it.z, it.x, it.y) }, // 12
            points.map { Point3D(-it.x, it.y, -it.z) }, // 13
            points.map { Point3D(-it.y, -it.x, -it.z) }, // 14
            points.map { Point3D(it.x, -it.y, -it.z) }, // 15
            points.map { Point3D(it.y, it.x, -it.z) }, // 16
            points.map { Point3D(-it.z, -it.y, -it.x) }, // 17
            points.map { Point3D(it.y, -it.z, -it.x) }, // 18
            points.map { Point3D(it.z, it.y, -it.x) }, // 19
            points.map { Point3D(-it.y, it.z, -it.x) }, // 20
            points.map { Point3D(-it.z, it.x, -it.y) }, // 21
            points.map { Point3D(-it.x, -it.z, -it.y) }, // 22
            points.map { Point3D(it.z, -it.x, -it.y) }, // 23
            points.map { Point3D(it.x, it.z, -it.y) }, // 24
        )
    }
}

fun main(){
    val input = getText("day19.txt")
    val task1 = findBeacons(input)
    println(task1)
    val task2 = largestDistBetweenScanners()
    println(task2)
}

fun largestDistBetweenScanners(): Int{
    var max = 0
    for(scanner1 in scannerLocationsFromScanner0){
        for(scanner2 in scannerLocationsFromScanner0){
            val dist = abs(scanner1.x-scanner2.x) + abs(scanner1.y-scanner2.y) + abs(scanner1.z-scanner2.z)
            if(dist > max) max = dist
        }
    }
    return max
}