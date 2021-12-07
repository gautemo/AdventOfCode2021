package shared

import java.io.File

fun getLines(filename: String) = File(Thread.currentThread().contextClassLoader.getResource(filename)!!.toURI()).readLines()

fun getLinesAsInt(filename: String) = getLines(filename).map { it.toInt() }

fun getText(filename: String) = File(Thread.currentThread().contextClassLoader.getResource(filename)!!.toURI()).readText().trim()