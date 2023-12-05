package com.example.consumer.controller

import com.amazonaws.services.s3.model.S3ObjectSummary
import com.example.consumer.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/s3")
class S3Controller(val personService: PersonService) {

    @GetMapping("/{bucketName}")
    fun getObjects(@PathVariable bucketName: String): ResponseEntity<List<S3ObjectSummary>> {
        return ResponseEntity.ok(personService.listObjects(bucketName))
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{bucketName}")
    fun deleteObject(
            @PathVariable bucketName: String,
            @RequestParam objectName: String
    ) {
        personService.deleteObject(bucketName, objectName)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{bucketName}/{objectName}")
    fun moveObject(
            @PathVariable bucketName: String,
            @PathVariable objectName: String,
            @RequestParam bucketTargetName: String
    ) {
        personService.moveObject(bucketName, objectName, bucketTargetName)
    }
}