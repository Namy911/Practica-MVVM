package com.example.practica.ui.main

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica.data.entity.User
import com.example.practica.data.entity.UserAndArticle
import com.example.practica.repository.RosterRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val rosterRepo: RosterRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _listUsers: MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
    val listUser = _listUsers

    private val _listUsersAndArticle: MutableLiveData<List<UserAndArticle>> by lazy { MutableLiveData<List<UserAndArticle>>() }
    val listUsersAndArticle = _listUsersAndArticle

    init {
        loadAll()
        loadUserAndArticleAll()
    }
    fun loadAll() {
        viewModelScope.launch {
            rosterRepo.loadAll().collect {

                _listUsers.value = it
            }
        }
    }
    fun loadUserAndArticleAll() {
        viewModelScope.launch {
            rosterRepo.loadUserAndArticleAll().collect {
                _listUsersAndArticle.value = setListUserArticles(it)
            }
        }
    }

    private fun setListUserArticles(item: List<UserAndArticle>): MutableList<UserAndArticle> {
        var result = mutableListOf<UserAndArticle>()
        item.forEach {
            if (it.article.size != 1) {
                for (element in it.article) {
                    result.add(UserAndArticle(it.user, listOf(element)))
                }
            }
        }
        result.addAll(item.filter { it.article.size == 1 })
        return result
    }
}