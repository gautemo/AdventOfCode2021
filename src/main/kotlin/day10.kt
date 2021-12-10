import shared.getLines

private val invalidRightParantheses = Regex("""[\[<{]\)""")
private val invalidRightSquareBracket = Regex("""[(<{]]""")
private val invalidRightSquirlyBracket = Regex("""[\[<(]}""")
private val invalidRightCrocodile = Regex("""[\[({]>""")

fun totalSyntaxScore(lines: List<String>): Int{
    var rightParantheses = 0
    var rightSquareBracket = 0
    var rightSquirlyBracket = 0
    var rightCrocodile = 0
    for(line in lines){
        val codeLeft = removeMatches(line)
        rightParantheses += invalidRightParantheses.findAll(codeLeft).count()
        rightSquareBracket += invalidRightSquareBracket.findAll(codeLeft).count()
        rightSquirlyBracket += invalidRightSquirlyBracket.findAll(codeLeft).count()
        rightCrocodile += invalidRightCrocodile.findAll(codeLeft).count()
    }
    return rightParantheses * 3 + rightSquareBracket * 57 + rightSquirlyBracket * 1197 + rightCrocodile * 25137
}

private fun invalid(code: String): Boolean{
    val codeLeft = removeMatches(code)
    return invalidRightParantheses.containsMatchIn(codeLeft) ||
            invalidRightSquareBracket.containsMatchIn(codeLeft) ||
            invalidRightSquirlyBracket.containsMatchIn(codeLeft) ||
            invalidRightCrocodile.containsMatchIn(codeLeft)
}

fun autocompleteScore(lines: List<String>): Long{
    val scores = lines.filter { !invalid(it) }.map { completeScore(it) }.sorted()
    return scores[scores.size/2]
}

private fun completeScore(code: String): Long{
    val score = mapOf(
        Pair(')', 1),
        Pair(']', 2),
        Pair('}', 3),
        Pair('>', 4),
    )
    val complete = complete(code)
    val added = complete.replace(code, "")
    return added.fold(0){ sum, c -> sum * 5 + score[c]!! }
}

private fun complete(code: String): String{
    if(removeMatches(code).isEmpty()) return code
    if(!invalid("$code)")) return complete("$code)")
    if(!invalid("$code]")) return complete("$code]")
    if(!invalid("$code}")) return complete("$code}")
    if(!invalid("$code>")) return complete("$code>")
    throw Exception("Should be valid symbol inserted before this")
}

private fun removeMatches(code: String): String{
    val changed = popMatch(code)
    return if(changed == code) code else removeMatches(changed)
}

private fun popMatch(code: String) = code.replace(Regex("""\(\)|\[]|<>|\{}"""), "")

fun main(){
    val lines = getLines("day10.txt")
    val task1 = totalSyntaxScore(lines)
    println(task1)
    val task2 = autocompleteScore(lines)
    println(task2)
}
