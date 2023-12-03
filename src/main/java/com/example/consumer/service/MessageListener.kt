package com.example.consumer.service

interface MessageListener {

    fun receive(message: String)
}