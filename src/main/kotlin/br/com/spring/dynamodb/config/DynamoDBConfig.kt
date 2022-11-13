package br.com.spring.dynamodb.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI

@Configuration
class DynamoDBConfig(
    private val env: Environment,
    @Value("\${aws.dynamo.endpoint}")
    private val endpoint: String,

    @Value("\${aws.accesskey}")
    private val accessKey: String,

    @Value("\${aws.secretkey}")
    private val secretKey: String

) {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun dynamoDb(): DynamoDbEnhancedAsyncClient {
        return DynamoDbEnhancedAsyncClient.builder().dynamoDbClient(dynamoDBConfiguration()).build()
    }

    fun dynamoDBConfiguration(): DynamoDbAsyncClient {
        logger.info { "Amazon DYNAMODB running with profile = ${env.activeProfiles.contentToString()}" }
        return DynamoDbAsyncClient.builder().region(Region.US_EAST_1)
            .endpointOverride(URI(endpoint))
            .credentialsProvider { AwsBasicCredentials.create(accessKey, secretKey) }.build()
    }
}
