package com.example.movieappcleanarichitecture.data.core

sealed class Either<out A, out B> {
    data class Left<out A> (val left: A) : Either<A, Nothing>()

    data class Right<out B> (val right: B) : Either<Nothing, B>()

    val isRight get() = this is Right<B>

    val isLeft get() = this is Left<A>

    fun <T> fold(fnL: (A) -> T, fnR: (B) -> T): T =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)

        }

    fun <T> T.toRight(): Right<T> =
        Right(this)

    fun <T> T.toLeft(): Left<T> =
        Left(this)
}