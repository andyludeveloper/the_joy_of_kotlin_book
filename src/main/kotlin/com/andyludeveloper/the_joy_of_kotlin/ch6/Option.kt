package com.andyludeveloper.the_joy_of_kotlin.ch6

sealed class Option<out A> {
    abstract fun isEmpty(): Boolean
    fun getOrElse(default: @UnsafeVariance A): A = when (this) {
        is None -> default
        is Some -> value
    }

    internal object None : Option<Nothing>() {

        override fun isEmpty(): Boolean = true

        override fun equals(other: Any?): Boolean = other == None

        override fun hashCode(): Int = 0

        override fun toString(): String = "None"
    }

    internal data class Some<out A>(internal val value: A) : Option<A>() {

        override fun isEmpty(): Boolean = false
    }

    companion object {
        operator fun <A> invoke(a: A? = null): Option<A> = when (a) {
            null -> None
            else -> Some(a)
        }
    }
}


fun main() {
    val max1 = max(listOf(4, 3, 2, 8)).getOrElse(1)
    println(max1)
    val max2 = max(listOf()).getOrElse(0)
    println(max2)
}

fun max(list: List<Int>): Option<Int> {
    return Option(list.maxOrNull())
}
