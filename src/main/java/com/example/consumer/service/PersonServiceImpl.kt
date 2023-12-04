package com.example.consumer.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.*
import com.example.consumer.model.Person
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter


@Service
class PersonServiceImpl(
        val amazonS3Client: AmazonS3
): PersonService {
    override fun process(person: Person) {

    }

    override fun createS3Bucket(bucketName: String, publicBucket: Boolean) {
        if(amazonS3Client.doesBucketExist(bucketName)) {
            return;
        }
        if(publicBucket) {
            amazonS3Client.createBucket(bucketName);
        } else {
            amazonS3Client.createBucket(CreateBucketRequest(bucketName).withCannedAcl(CannedAccessControlList.Private));
        }
    }

    override fun listBuckets(): List<Bucket>{
        return amazonS3Client.listBuckets();
    }

    override fun deleteBucket(bucketName: String){
        //TODO: catch exception here
        amazonS3Client.deleteBucket(bucketName);
    }

    //Object level operations
    @Throws(IOException::class)
    override fun putObject(bucketName: String?, objectName: String, objectValue: Person, publicObject: Boolean) {
        val file = File("." + File.separator + objectName)
        val fileWriter = FileWriter(file, false)
        val printWriter = PrintWriter(fileWriter)
        printWriter.println(objectValue)
        printWriter.flush()
        printWriter.close()
        if (publicObject) {
            val putObjectRequest = PutObjectRequest(bucketName, objectName, file).withCannedAcl(CannedAccessControlList.PublicRead)
            amazonS3Client.putObject(putObjectRequest)
        } else {
            val putObjectRequest = PutObjectRequest(bucketName, objectName, file).withCannedAcl(CannedAccessControlList.Private)
            amazonS3Client.putObject(putObjectRequest)
        }
    }

    override fun listObjects(bucketName: String): List<S3ObjectSummary> {
        val objectListing = amazonS3Client.listObjects(bucketName);
        return objectListing.objectSummaries;
    }

    override fun downloadObject(bucketName: String, objectName:String) {
        //TODO: catch exceptions
        val s3object = amazonS3Client.getObject(bucketName, objectName);
        FileUtils.copyInputStreamToFile(s3object.objectContent, File("." + File.separator + objectName));
    }

    override fun deleteObject(bucketName: String, objectName:String) {
        amazonS3Client.deleteObject(bucketName, objectName);
    }

    override fun moveObject(bucketSourceName: String, objectName: String, bucketTargetName: String){
        amazonS3Client.copyObject(
                bucketSourceName,
                objectName,
                bucketTargetName,
                objectName
        );
    }

}