package com.example.consumer.service

import com.example.consumer.model.PersonMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MessageListenerImpl(
        val personService: PersonService,
        val personMapper: PersonMapper
): MessageListener {

    @KafkaListener(id = "group_id", topics = ["person"])
    override fun receive(message: String) {
        val person = personMapper.map(message)
        personService.putObject("bucket-1", person.name.toString(), person, true)
    }

}