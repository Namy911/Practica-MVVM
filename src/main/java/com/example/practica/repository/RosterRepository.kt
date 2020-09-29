package com.example.practica.repository

import com.example.practica.data.entity.*
import javax.inject.Inject

class RosterRepository @Inject constructor(
    private val storeArticle: Article.Store,
    private val storeCategory: Category.Store,
    private val storeCategoryArticle: CategoryAndArticle.Store,
    private val storeUserArticle: UserAndArticle.Store) {

    // Load all categories from dialog(select category on insert/update)
    fun loadAllCategories() =
        storeCategory.loadAllDistinct()


    suspend fun saveArticle(article: Article) =
        storeArticle.save(article)

    suspend fun deleteArticle(article: Article) =
        storeArticle.delete(article)

    // CardView content on start app
    fun loadUserAndArticleAll() =
        storeUserArticle.loadAllDistinct()

    // Load all articles by category
    fun loadCategoryAndArticle() =
        storeCategoryArticle.loadAllDistinct()

    // Load 1 article(model:Article) by id
    fun loadArticle(id: Int) =
        storeArticle.loadArticle(id)

    // Load 1 article(model:CategoryAndArticle) by id
    fun loadArticleToEdit(id: Int) =
        storeCategoryArticle.loadArticleToEdit(id)

}