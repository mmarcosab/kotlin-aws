package com.example.consumer.model

import org.springframework.stereotype.Component
import com.example.consumer.utils.Mapper
import com.google.gson.Gson

@Component
class PersonMapper: Mapper<String, Person> {
    override fun map(t: String): Person {
        return Gson().fromJson(t, Person::class.java)
    }

}