package com.example.consumer.service

import com.example.consumer.model.Person

interface PersonService {
    fun process(person: Person)
}