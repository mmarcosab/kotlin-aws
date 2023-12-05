package com.example.consumer.service

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.ReceiveMessageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SQSListenerImpl(
        val amazonSQS: AmazonSQS
): SQSListener {

    @Scheduled(fixedDelay = 50000)
    override fun receive() {
        val receiveMessageRequest = ReceiveMessageRequest()
        receiveMessageRequest.queueUrl = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/test-queue"
        receiveMessageRequest.waitTimeSeconds = 5
        receiveMessageRequest.maxNumberOfMessages = 5
        val messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages()
        println(messages.toString())
    }

}