package com.example.practica.repository

import com.example.practica.data.entity.User
import javax.inject.Inject

class RosterRepository @Inject constructor(private val store: User.Store) {

    fun loadAll() =
        store.loadAllDistinct()

}