package com.example.practica.repository

import com.example.practica.data.entity.Article
import com.example.practica.data.entity.CategoryAndArticle
import com.example.practica.data.entity.User
import com.example.practica.data.entity.UserAndArticle
import javax.inject.Inject

class RosterRepository @Inject constructor(
    private val storeUser: User.Store,
    private val storeArticle: Article.Store,
    private val storeCategory: CategoryAndArticle.Store,
    private val storeUserArticle: UserAndArticle.Store) {

    // ********************
    fun loadAll() =
        storeUser.loadAllDistinct()

    // CardView content on start app
    fun loadUserAndArticleAll() =
        storeUserArticle.loadAllDistinct()

    // Load all articles by category
    fun loadCategoryAndArticle() =
        storeCategory.loadAllDistinct()

    // Load 1 article(model:Article) by id
    fun loadArticle(id: Int) =
        storeArticle.loadArticle(id)

    // Load 1 article(model:CategoryAndArticle) by id
    fun loadArticleToEdit(id: Int) =
        storeCategory.loadArticleToEdit(id)

}