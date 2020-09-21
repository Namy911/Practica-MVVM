package com.example.practica.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica.data.entity.User
import com.example.practica.repository.RosterRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val rosterRepo: RosterRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _listUsers: MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
    val listUser = _listUsers

    init {
        loadAll()
    }
    fun loadAll() {
        viewModelScope.launch {
            rosterRepo.loadAll().collect {
                listUser.value = it
            }
        }
    }
}