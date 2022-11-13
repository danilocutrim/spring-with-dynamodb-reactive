package br.com.spring.dynamodb.repository

interface UserRepositoryInterface<T> {
    suspend fun save(entity: T): T
    suspend fun get(document: String, id: String): T
    suspend fun update(entity: T)
}
