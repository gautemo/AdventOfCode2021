fun findBeacons(input: String): Int{
    val beacons = input.split("\n\n").map { scanner ->
        scanner.lines().drop(1).map { beacon ->
            val points = beacon.split(',')
            Point3D(points[0].toInt(), points[1].toInt(), points[2].toInt())
        }
    }
    val scanner1 = beacons.first()
    val matches = beacons.drop(1).count { matchBeacons(scanner1, it) }
    return matches
}

private fun matchBeacons(scanner1: List<Point3D>, scanner2: List<Point3D>): Boolean {
    val referencePoint1 = scanner1.first()
    val rest1 = scanner1.drop(1)
    for(referencePoint2 in scanner2){
        val rest2 = scanner2.filter{ it != referencePoint2 }
        val count = 1 + rest2.count { hasDistPoint(referencePoint1, rest1, referencePoint2.x - it.x, referencePoint2.y - it.y, referencePoint2.z - it.z) }
        if(count >= 12) return true
    }
    return false
}

private fun hasDistPoint(reference: Point3D, rest: List<Point3D>, x: Int, y: Int, z: Int): Boolean{
    return rest.any { reference.x == it.x + x && reference.y == it.y + y && reference.z == it.z + z }
}

private fun allFlipFlops(scanner: List<Point3D>): List<List<Point3D>>{
    return listOf(
        scanner,
        scanner.map { Point3D(-it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, -it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, -it.z) },
        scanner.map { Point3D(-it.x, -it.y, it.z) },
        scanner.map { Point3D(-it.x, it.y, -it.z) },
        scanner.map { Point3D(it.x, -it.y, -it.z) },
        scanner.map { Point3D(-it.x, -it.y, -it.z) },

        scanner.map { Point3D(it.x, it.z, it.y) },
        scanner.map { Point3D(it.x, -it.z, it.y) },
        scanner.map { Point3D(it.x, it.z, -it.y) },

        scanner.map { Point3D(it.y, it.x, it.z) },
        scanner.map { Point3D(-it.y, it.x, it.z) },
        scanner.map { Point3D(it.y, -it.x, it.z) },

        scanner.map { Point3D(it.z, it.y, it.x) },
        scanner.map { Point3D(-it.z, it.y, it.x) },
        scanner.map { Point3D(it.z, it.y, -it.x) },


        scanner.map { Point3D(it.y, it.z, it.x) },
        scanner.map { Point3D(it.z, it.x, it.y) },
        scanner.map { Point3D(it.z, it.y, it.x) },

        scanner.map { Point3D(it.x, it.z, it.y) },
        scanner.map { Point3D(it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, it.z) },
        scanner.map { Point3D(it.x, it.y, it.z) },
    )
}

data class Point3D(val x: Int, val y: Int, val z: Int)