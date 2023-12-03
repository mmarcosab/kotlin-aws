package com.example.consumer.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsConfig {

    class AWSConfig {
        fun credentials(): AWSCredentials {
            return BasicAWSCredentials(
                    "test",
                    "test"
            )
        }

        @Bean
        fun amazonS3Client(): AmazonS3 {
            return AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(AWSStaticCredentialsProvider(credentials()))
                    .withEndpointConfiguration(getEndpointConfiguration("http://s3.localhost.localstack.cloud:4566"))
                    .build()
        }

        private fun getEndpointConfiguration(url: String): AwsClientBuilder.EndpointConfiguration {
            return AwsClientBuilder.EndpointConfiguration(url, Regions.US_EAST_1.getName())
        }
    }

}