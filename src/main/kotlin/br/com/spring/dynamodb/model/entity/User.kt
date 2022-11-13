package br.com.spring.dynamodb.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey
import java.io.Serializable
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
@DynamoDbBean
class User(

    @get:DynamoDbSortKey
    var userId: String? = UUID.randomUUID().toString(),

    @get:DynamoDbPartitionKey
    var document: String? = null,

    @get:DynamoDbAttribute(value = "name")
    var name: String? = null,

    @get:DynamoDbAttribute(value = "lastName")
    var lastName: String? = null,

    @get:DynamoDbAttribute(value = "birthDate")
    var birthDate: String? = null,

    @get:DynamoDbAttribute(value = "nickName")
    var nickName: String? = null,

    @get:DynamoDbAttribute(value = "country")
    var country: String? = null,

) : Serializable
