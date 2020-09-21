package com.example.practica.data.entity

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


data class UserAndArticleRef(
    @Embedded val user: User,
    @Embedded val article: Article
)
data class UserAndArticle(
    @Embedded val user: User,
    @Relation(
        parentColumn = "_id",
        entityColumn = "user_id",
        entity = Article::class
    )
    val article: List<Article> = listOf()
){
    @Dao
    interface Store{
        @Transaction
        @Query("SELECT * FROM `users`")
        fun loadAll(): Flow<List<UserAndArticle>>

        fun loadAllDistinct() =
            loadAll().distinctUntilChanged()
    }
}