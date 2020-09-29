package com.example.practica.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.practica.data.entity.*
import com.example.practica.repository.RosterRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val rosterRepo: RosterRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _listUsersAndArticle: MutableLiveData<List<UserAndArticle>> by lazy { MutableLiveData<List<UserAndArticle>>() }
    val listUsersAndArticle = _listUsersAndArticle

    private val _listCategoryAndArticle: MutableLiveData<List<CategoryAndArticle>> by lazy { MutableLiveData<List<CategoryAndArticle>>() }
    val listCategoryAndArticle = _listCategoryAndArticle

    var article: LiveData<Article> =  MediatorLiveData()

    var articleToEdit: LiveData<CategoryAndArticle> =  MediatorLiveData()
    private val _categories: MutableLiveData<List<Category>>  by lazy { MutableLiveData<List<Category>>() }
     val categories = _categories

    init {
        loadAllCategories()
        loadAllUserAndArticle()
        loadAllCategoryAndArticle()
    }

    // Load all categories from dialog(select category on insert/update)
    private fun loadAllCategories() {
        viewModelScope.launch {
             rosterRepo.loadAllCategories().collect{
                 _categories.value = it
            }
        }
    }

    // Insert Article
    fun saveArticle(article: Article){
        viewModelScope.launch {
            rosterRepo.saveArticle(article)
        }
    }
    fun deleteArticle(article: Article){
        viewModelScope.launch {
            rosterRepo.deleteArticle(article)
        }
    }

    // Load 1 article(model:Article) by id
    fun loadArticle(id : Int ) {
        viewModelScope.launch {
            article  =  rosterRepo.loadArticle(id)
        }
    }

    // Load 1 article(model:CategoryAndArticle) by id
    fun loadArticleToEdit(id: Int){
        viewModelScope.launch {
            articleToEdit = rosterRepo.loadArticleToEdit(id)
        }
    }

    // CardView content on start app
    private fun loadAllUserAndArticle() {
        viewModelScope.launch {
            rosterRepo.loadUserAndArticleAll().collect {
                _listUsersAndArticle.value = setListUserArticles(it)
            }
        }
    }

    // ********************
    private fun loadAllCategoryAndArticle() {
        viewModelScope.launch {
            rosterRepo.loadCategoryAndArticle().collect {
                _listCategoryAndArticle.value = setListCategoryArticles(it)
            }
        }
    }

    // Create correct list 1:1 (user:article)
    private fun setListUserArticles(item: List<UserAndArticle>): MutableList<UserAndArticle> {
        val result = mutableListOf<UserAndArticle>()
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

    // Create correct list 1:1 (category:article)
    private fun setListCategoryArticles(item: List<CategoryAndArticle>): MutableList<CategoryAndArticle> {
        val result = mutableListOf<CategoryAndArticle>()
        item.forEach {
            if (it.article.size != 1) {
                for (element in it.article) {
                    result.add(CategoryAndArticle(it.category, listOf(element)))
                }
            }
        }
        result.addAll(item.filter { it.article.size == 1 })
        return result
    }
}