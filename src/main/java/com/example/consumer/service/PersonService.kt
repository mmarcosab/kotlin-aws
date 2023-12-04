package com.example.consumer.service

import com.amazonaws.services.s3.model.*
import com.example.consumer.model.Person

interface PersonService {
    fun process(person: Person)
    fun createS3Bucket(bucketName: String, publicBucket: Boolean)
    fun listBuckets(): List<Bucket>
    fun deleteBucket(bucketName: String)
    fun putObject(bucketName: String?, objectName: String, objectValue: Person, publicObject: Boolean)
    fun listObjects(bucketName: String): List<S3ObjectSummary>
    fun downloadObject(bucketName: String, objectName:String)
    fun deleteObject(bucketName: String, objectName:String)
    fun moveObject(bucketSourceName: String, objectName: String, bucketTargetName: String)
}