package com.example.practica.data.entity

import androidx.room.*
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_USER_ID
import com.example.practica.data.db.TaskSchema.UserTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

data class UserAndArticle(
    @Embedded val user: User,
    @Relation(
        parentColumn = UserTable.ROW_ID,
        entityColumn = ROW_USER_ID,
        entity = Article::class
    )
    val article: List<Article> = listOf()
){
    @Dao
    interface Store{
        @Transaction
        @Query("SELECT * FROM ${UserTable.TABLE_NAME}")
        fun loadAll(): Flow<List<UserAndArticle>>

        fun loadAllDistinct() =
            loadAll().distinctUntilChanged()
    }
}