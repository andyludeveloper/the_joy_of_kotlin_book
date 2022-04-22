package com.andyludeveloper.the_joy_of_kotlin.ch6

import kotlin.math.*

data class Toon(
    val firstName: String,
    val lastName: String,
    val email: Option<String> = Option()
) {
    companion object {
        operator fun invoke(
            firstName: String,
            lastName: String,
            email: String? = null
        ) =
            Toon(firstName, lastName, Option(email))
    }
}

fun <K, V> Map<K, V>.getOption(key: K) = Option(this[key])

val variance: (List<Double>) -> Option<Double> = { list ->
    mean(list).flatMap { m ->
        mean(list.map { x ->
            (x - m).pow(2.0)
        })
    }
}

val mean: (List<Double>) -> Option<Double> = { list ->
    when {
        list.isEmpty() -> Option()
        else -> Option(list.sum() / list.size)
    }
}

fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> = {
    try {
        it.map(f)
    } catch (exception: Exception) {
        Option()
    }
}

fun <A, B> hLift(f: (A) -> B): (A) -> Option<B> = {
    try {
        Option(it).map(f)
    }catch (e:Exception){
        Option()
    }
}
fun main() {
    val toons: Map<String, Toon> = mapOf(
        "Mickey" to Toon("Mickey", "Mouse", "mickey@disney.com"),
        "Minnie" to Toon("Minnie", "Mouse"),
        "Donald" to Toon("Donald", "Duck", "donald@disney.com")
    )
    val mickey = toons.getOption("Mickey").flatMap { it.email }
    val minnie = toons.getOption("Minnie").flatMap { it.email }
    val goofy = toons.getOption("Goofy").flatMap { it.email }

    println(mickey.getOrElse { " No Data" })
    println(minnie.getOrElse { " No Data" })
    println(goofy.getOrElse { " No Data" })

    val mickey2 = toons["Mickey"]?.email ?: "No Data"
    val minnie2 = toons["Minnie"]?.email ?: "No Data"
    val goofy2 = toons["Goofy"]?.email ?: "No Data"

    println(mickey2)
    println(minnie2)
    println(goofy2)
}