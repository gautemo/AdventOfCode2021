import shared.getText

fun toBits(input: String): Bits{
    return Bits(input.map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString(""))
}

fun versionSum(packets: List<Packet>): Int{
    return packets.sumOf { it.version + versionSum(it.subPackets) }
}

fun evaluatePackets(packet: Packet): Long{
    return when(packet.typeID){
        4 -> packet.literalValue!!
        0 -> packet.subPackets.sumOf { evaluatePackets(it) }
        1 -> packet.subPackets.fold(1) { sum, p -> sum * evaluatePackets(p) }
        2 -> packet.subPackets.minOf { evaluatePackets(it) }
        3 -> packet.subPackets.maxOf { evaluatePackets(it) }
        5 -> if(evaluatePackets(packet.subPackets.first()) > evaluatePackets(packet.subPackets.last())) 1 else 0
        6 -> if(evaluatePackets(packet.subPackets.first()) < evaluatePackets(packet.subPackets.last())) 1 else 0
        7 -> if(evaluatePackets(packet.subPackets.first()) == evaluatePackets(packet.subPackets.last())) 1 else 0
        else -> throw Exception("should match on type")
    }
}

fun toPackets(bits: Bits, maxPackets: Int? = null): List<Packet>{
    val packets = mutableListOf<Packet>()
    while(!bits.isEmpty()){
        if(maxPackets != null && packets.size == maxPackets) break
        packets.add(toPacket(bits))
    }
    return packets
}

private fun toPacket(bits: Bits): Packet {
    val version = bits.take(3).toInt(2)
    val typeID = bits.take(3).toInt(2)
    if(typeID == 4) {
        var literalValue = ""
        for (group in bits.value.chunked(5)) {
            bits.drop(5)
            literalValue += group.drop(1)
            if (group.startsWith('0')) break
        }
        return Packet(version, typeID, literalValue.toLong(2))
    }else{
        val lengthType = bits.take(1)
        return if(lengthType == "0"){
            val L = bits.take(15).toInt(2)
            val subpackets = toPackets(Bits(bits.take(L)))
            Packet(version, typeID, subPackets = subpackets)
        }else{
            val L = bits.take(11).toInt(2)
            val subpackets = toPackets(bits, L)
            Packet(version, typeID, subPackets = subpackets)
        }
    }
}

data class Packet(val version: Int, val typeID: Int, val literalValue: Long? = null, val subPackets: List<Packet> = listOf())

data class Bits(var value: String){
    fun take(n: Int): String {
        val taken = value.take(n)
        value = value.drop(n)
        return taken
    }

    fun drop(n: Int){
        value = value.drop(n)
    }

    fun isEmpty() = value.isEmpty() || value.all { it == '0' }
}

fun main(){
    val input = getText("day16.txt")
    val bits = toBits(input)
    val packets = toPackets(bits)
    val task1 = versionSum(packets)
    println(task1)
    val task2 = evaluatePackets(packets.first())
    println(task2)
}