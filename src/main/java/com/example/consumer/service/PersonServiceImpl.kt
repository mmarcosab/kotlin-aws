package com.example.consumer.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.*
import com.example.consumer.model.Person
import org.apache.commons.io.FileUtils;

import org.springframework.stereotype.Service
import java.io.File

@Service
class PersonServiceImpl(
        val amazonS3Client: AmazonS3
): PersonService {
    override fun process(person: Person) {

    }

    fun createS3Bucket(bucketName: String, publicBucket: Boolean) {
        if(amazonS3Client.doesBucketExist(bucketName)) {
            return;
        }
        if(publicBucket) {
            amazonS3Client.createBucket(bucketName);
        } else {
            amazonS3Client.createBucket(CreateBucketRequest(bucketName).withCannedAcl(CannedAccessControlList.Private));
        }
    }

    fun listBuckets(): List<Bucket>{
        return amazonS3Client.listBuckets();
    }

    fun deleteBucket(bucketName: String){
        //TODO: catch exception here
        amazonS3Client.deleteBucket(bucketName);
    }

    //Object level operations

    fun listObjects(bucketName: String): List<S3ObjectSummary> {
        val objectListing = amazonS3Client.listObjects(bucketName);
        return objectListing.objectSummaries;
    }

    fun downloadObject(bucketName: String, objectName:String) {
        //TODO: catch exceptions
        val s3object = amazonS3Client.getObject(bucketName, objectName);
        FileUtils.copyInputStreamToFile(s3object.objectContent, File("." + File.separator + objectName));
    }

    fun deleteObject(bucketName: String, objectName:String) {
        amazonS3Client.deleteObject(bucketName, objectName);
    }

    fun moveObject(bucketSourceName: String, objectName: String, bucketTargetName: String){
        amazonS3Client.copyObject(
                bucketSourceName,
                objectName,
                bucketTargetName,
                objectName
        );
    }

}