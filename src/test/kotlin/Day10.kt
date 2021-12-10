import kotlin.test.Test
import kotlin.test.assertEquals

class Day10 {
    @Test
    fun `total syntax error score is 26397`(){
        val result = totalSyntaxScore(input)
        assertEquals(26397, result)
    }

    @Test
    fun `middle score of complete lines is 288957`(){
        val result = autocompleteScore(input)
        assertEquals(288957, result)
    }

    companion object{
        val input = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent().lines()
    }
}