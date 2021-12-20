import shared.getText

var outsideLit = false

fun processImage(input: String): Int{
    val (algorithm, image) = input.split("\n\n")
    var enhanced = image
    repeat(2){
        enhanced = processImg(algorithm, enhanced)
        if(!outsideLit && algorithm[0] == '#') outsideLit = true
        if(outsideLit && algorithm.last() == '.') outsideLit = false
        println(enhanced)
        println()
    }
    return enhanced.count { it == '#' }
}

private fun processImg(algorithm: String, image: String): String{
    var newImg = ""
    val yLength = image.lines().size+1
    val xLength = image.lines().first().length+1
    for(y in -2..yLength){
        for(x in -2..xLength){
            val algoIndex = listOf(
                image.getPixel(x-1,y-1),
                image.getPixel(x,y-1),
                image.getPixel(x+1,y-1),
                image.getPixel(x-1,y),
                image.getPixel(x,y),
                image.getPixel(x+1,y),
                image.getPixel(x-1,y+1),
                image.getPixel(x,y+1),
                image.getPixel(x+1,y+1),
            ).map { if(it == '#') '1' else '0' }.joinToString("").toInt(2)
            newImg += algorithm[algoIndex]
        }
        if(y != yLength) newImg += "\n"
    }
    return newImg
}

fun String.getPixel(x: Int, y: Int): Char{
    if(x < 0 ||
        y < 0 ||
        x > this.lines().first().length - 1 ||
        y > this.lines().size - 1
    ) return if(outsideLit) '#' else '.'
    return this.lines()[y][x]
}

fun main(){
    val input = getText("day20.txt")
    val task1 = processImage(input)
    println(task1)
}