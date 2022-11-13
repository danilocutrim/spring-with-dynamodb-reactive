package br.com.spring.dynamodb.repository

import br.com.spring.dynamodb.constants.TABLE_NAME
import br.com.spring.dynamodb.constants.USER_NOT_FOUND
import br.com.spring.dynamodb.exception.NotFoundException
import br.com.spring.dynamodb.model.entity.User
import kotlinx.coroutines.future.await
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest

@Repository
class UserRepository(private val dynamoDb: DynamoDbEnhancedAsyncClient) :
    UserRepositoryInterface<User> {

    val logger = KotlinLogging.logger { }
    val table: DynamoDbAsyncTable<User> = dynamoDb.table(TABLE_NAME, TableSchema.fromBean(User::class.java))

    override suspend fun save(entity: User): User {
        logger.debug { "save: Saving user, document: ${entity.document}" }
        table.putItem(entity).await()
        return entity.also {
            logger.debug { "save: Save user, document: ${entity.document}" }
        }
    }

    override suspend fun get(document: String, id: String): User {
        return table.getItem(Key.builder().partitionValue(document).sortValue(id).build()).await()
            ?: throw NotFoundException(
                USER_NOT_FOUND
            )
    }

    override suspend fun update(entity: User) {
        val updateItemEnhancedRequest = UpdateItemEnhancedRequest
            .builder(User::class.java).item(entity).build()
        table.updateItem(updateItemEnhancedRequest).await()
    }
//
//    override suspend fun update(entity: User) {
//
//        logger.debug {
//            "update: update user," +
//                    " document: ${entity.document}" +
//                    " id: ${entity.userId}"
//        }
//        val updateItemSpec = UpdateItemSpec().withPrimaryKey(
//            "document",
//            entity.document,
//            "userId",
//            entity.userId
//        )
//            .withUpdateExpression(
//                " set birthDate = :bd," +
//                        " country = :ct," +
//                        " lastName = :ln," +
//                        " userName = :na," +
//                        " nickName = :nn"
//            )
//            .withValueMap(
//                ValueMap()
//                    .withString(":bd", entity.birthDate)
//                    .withString(":nn", entity.nickName)
//                    .withString(":ct", entity.country)
//                    .withString(":ln", entity.lastName)
//                    .withString(":na", entity.name)
//            )
//            .withReturnValues(ReturnValue.UPDATED_NEW)
//        dynamoDb.getTable(TABLE_NAME).updateItem(updateItemSpec)
//
//        logger.debug {
//            "update: updated user Success," +
//                    " document: ${entity.document}" +
//                    " id: ${entity.userId}"
//        }
//    }
//
//    fun listAll(document: String): User? {
//        return mapper.load(User::class.java, document)
//    }
}
