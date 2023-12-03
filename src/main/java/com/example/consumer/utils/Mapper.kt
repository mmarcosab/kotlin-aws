package com.example.consumer.utils

interface Mapper<T, U> {
    fun map(t: T): U
}