package com.example.practica.data.entity

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.practica.data.db.TaskSchema
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_CATEGORY_ID
import com.example.practica.data.db.TaskSchema.CategoryTable
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


data class CategoryAndArticle (
    @Embedded val category: Category,
    @Relation(
        parentColumn = CategoryTable.ROW_ID,
        entityColumn = ROW_CATEGORY_ID,
        entity = Article::class
    )
    val article: List<Article> = listOf()
){
    @Dao
    interface Store{

        @Transaction
        @Query("SELECT * FROM $TABLE_NAME")
        fun loadAll(): Flow<List<CategoryAndArticle>>

        fun loadAllDistinct() =
            loadAll().distinctUntilChanged()

        @Transaction
        @Query("SELECT * FROM $TABLE_NAME")
        fun loadArticle(): Flow<CategoryAndArticle>

        fun loadArticleDistinct(id: Int) =
            loadArticle().distinctUntilChanged()

        @Transaction
        @Query("SELECT * FROM $TABLE_NAME WHERE ${CategoryTable.ROW_ID} = :id")
        fun loadArticleToEdit(id: Int): LiveData<CategoryAndArticle>

//        @Insert(onConflict = OnConflictStrategy.REPLACE)
//        suspend fun _saveArticle(article: Article)
//
//        @Insert(onConflict = OnConflictStrategy.REPLACE)
//        suspend fun _saveCategory(category: Category)
//
//        @Transaction
//        suspend fun saveCategoryAndArticle(article: Article, category: Category){
//            _saveCategory(category)
//            _saveArticle(article)
//        }
    }
}