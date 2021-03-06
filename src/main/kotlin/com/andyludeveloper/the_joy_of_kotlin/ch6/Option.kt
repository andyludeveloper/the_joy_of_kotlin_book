package com.andyludeveloper.the_joy_of_kotlin.ch6

sealed class Option<out A> {
    abstract fun isEmpty(): Boolean
    abstract fun <B> map(f: (A) -> B): Option<B>

    //Exercise 6-1
//    fun getOrElse(default: @UnsafeVariance A): A = when (this) {
//        is None -> default
//        is Some -> value
//    }
    //Exercise 6-4
    fun <B> flatMap(f: (A) -> Option<B>): Option<B> = map(f).getOrElse { None }
    fun orElse(default: () -> Option<@UnsafeVariance A>): Option<A> =
        map { this }.getOrElse(default)

    fun filter(p: (A) -> Boolean): Option<A> = flatMap { x ->if(p(x)) this else None }

    //Exercise 6-2
    fun getOrElse(default: () -> @UnsafeVariance A): A = when (this) {
        is None -> default()
        is Some -> value
    }

    internal object None : Option<Nothing>() {

        override fun isEmpty(): Boolean = true

        override fun <B> map(f: (Nothing) -> B): Option<B> = None

        override fun equals(other: Any?): Boolean = other == None

        override fun hashCode(): Int = 0

        override fun toString(): String = "None"
    }

    internal data class Some<out A>(internal val value: A) : Option<A>() {

        override fun isEmpty(): Boolean = false

        override fun <B> map(f: (A) -> B): Option<B> = Some(f(value))
    }

    companion object {
        operator fun <A> invoke(a: A? = null): Option<A> = when (a) {
            null -> None
            else -> Some(a)
        }
    }
}

//Exercise 6-1
//fun main() {
//    val max1 = max(listOf(4, 3, 2, 8)).getOrElse(1)
//    println(max1)
//    val max2 = max(listOf()).getOrElse(0)
//    println(max2)
//}

//Exercise 6-2
fun getDefault(): Int = throw RuntimeException()
fun main() {
    val max1 = max(listOf(4, 3, 2, 8)).getOrElse(::getDefault)
    println(max1)
    val max2 = max(listOf()).getOrElse(::getDefault)
    println(max2)
}

fun max(list: List<Int>): Option<Int> {
    return Option(list.maxOrNull())
}
