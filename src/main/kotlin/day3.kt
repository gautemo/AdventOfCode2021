import shared.getLines

fun getPowerConsumption(diagnosticReport: List<String>): Int{
    val length = diagnosticReport.first().length
    var gamma = ""
    var epsilon = ""
    for(i in 0 until length){
        val ones = diagnosticReport.count { it[i] == '1' }
        val zeros = diagnosticReport.count { it[i] == '0' }
        if(ones > zeros){
            gamma += "1"
            epsilon += "0"
        }else{
            gamma += "0"
            epsilon += "1"
        }
    }
    return gamma.toInt(2) * epsilon.toInt(2)
}

fun getLifeSupport(diagnosticReport: List<String>): Int{
    return getMetric(diagnosticReport) { ones, zeros -> ones >= zeros } * getMetric(diagnosticReport) { ones, zeros -> ones < zeros }
}

private fun getMetric(input: List<String>, criteria: (ones: Int, zeros: Int) -> Boolean): Int{
    var diagnosticReport = input
    val length = diagnosticReport.first().length
    for(i in 0 until length){
        val ones = diagnosticReport.count { it[i] == '1' }
        val zeros = diagnosticReport.count { it[i] == '0' }
        val keep = if(criteria(ones, zeros)) '1' else '0'
        diagnosticReport = diagnosticReport.filter { it[i] == keep }
        if(diagnosticReport.size == 1){
            return diagnosticReport[0].toInt(2)
        }
    }
    throw Exception("Should stop with one diagnostic line")
}

fun main(){
    val input = getLines("day3.txt")
    val power = getPowerConsumption(input)
    println(power)
    val lifeSupport = getLifeSupport(input)
    println(lifeSupport)
}