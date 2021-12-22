import shared.getText

var outsideLit = false

fun processImage(input: String, times: Int = 2): Int{
    val (algorithm, image) = input.split("\n\n")
    var enhanced = image
    repeat(times){
        enhanced = processImg(algorithm, enhanced)
        if(outsideLit){
            if(algorithm.last() == '.') outsideLit = false
        }else{
            if(algorithm[0] == '#') outsideLit = true
        }
    }
    return enhanced.count { it == '#' }
}

private fun processImg(algorithm: String, image: String): String{
    val newImg = StringBuilder()
    val yLength = image.lines().size
    val xLength = image.lines().first().length
    for(y in -1..yLength){
        for(x in -1..xLength){
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
            newImg.append(algorithm[algoIndex])
        }
        if(y != yLength) newImg.append("\n")
    }
    return newImg.toString()
}

fun String.getPixel(x: Int, y: Int) = this.lines().getOrNull(y)?.getOrNull(x) ?: if(outsideLit) '#' else '.'

fun main(){
    val input = getText("day20.txt")
    val task1 = processImage(input)
    println(task1)
    val task2 = processImage(input, 50) // slow
    println(task2)
}