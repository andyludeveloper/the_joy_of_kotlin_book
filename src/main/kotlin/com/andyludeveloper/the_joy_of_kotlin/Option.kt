package com.andyludeveloper.the_joy_of_kotlin

sealed class Option<out A> {
    abstract fun isEmpty(): Boolean

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