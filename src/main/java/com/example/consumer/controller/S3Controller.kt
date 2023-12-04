package com.example.consumer.controller

import com.amazonaws.services.s3.model.S3ObjectSummary
import com.example.consumer.service.PersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/s3")
class S3Controller(val personService: PersonService) {

    @GetMapping("/{bucketName}")
    fun getObjects(@PathVariable bucketName: String): ResponseEntity<List<S3ObjectSummary>> {
        return ResponseEntity.ok(personService.listObjects(bucketName))
    }
}