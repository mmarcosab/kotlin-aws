package com.example.consumer.model

open class Person (val name: String?, val age: Int?, val surname: String?){

    var id: Int? = null
        get
        set

    constructor(id: Int?, name: String?, age: Int?, surname: String?) : this(name, age, surname) {
        this.id = id
    }

    override fun toString(): String {
        return "Person(id='$id', name='$name', age=$age, surname='$surname')"
    }
}