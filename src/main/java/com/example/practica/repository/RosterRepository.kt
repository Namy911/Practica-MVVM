package com.example.practica.repository

import com.example.practica.data.entity.User
import com.example.practica.data.entity.UserAndArticle
import javax.inject.Inject

class RosterRepository @Inject constructor(
    private val store: User.Store,
    private val storeUser: UserAndArticle.Store) {

    fun loadAll() =
        store.loadAllDistinct()

    fun loadUserAndArticleAll() =
        storeUser.loadAllDistinct()

}