import kotlin.test.Test
import kotlin.test.assertEquals

class Day16 {
    @Test
    fun `version sum is 16`(){
        val input = "8A004A801A8002F478"
        val bits = toBits(input)
        val packets = toPackets(bits)
        val result = versionSum(packets)
        assertEquals(16, result)
    }

    @Test
    fun `evaluate transmission`(){
        val parameters = listOf<Pair<String, Long>>(
            Pair("C200B40A82", 3),
            Pair("04005AC33890", 54),
            Pair("880086C3E88112", 7),
            Pair("CE00C43D881120", 9),
            Pair("D8005AC2A8F0", 1),
            Pair("F600BC2D8F", 0),
            Pair("9C005AC2F8F0", 0),
            Pair("9C0141080250320F1802104A08", 1),
        )

        fun test(input: String, expected: Long){
            val packets = toPackets(toBits(input))
            val result = evaluatePackets(packets.first())
            assertEquals(expected, result)
        }

        parameters.forEach { (input, expected) ->
            test(input, expected)
        }
    }
}