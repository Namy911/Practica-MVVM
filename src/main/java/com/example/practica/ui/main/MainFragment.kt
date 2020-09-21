package com.example.practica.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.practica.R
import com.example.practica.data.entity.Article
import com.example.practica.data.entity.User
import com.example.practica.data.entity.UserAndArticle
import com.example.practica.data.entity.UserAndArticleRef
import com.example.practica.repository.RosterRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
        private const val TAG = "MainFragment"
    }

    private val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listUsersAndArticle.observe(viewLifecycleOwner) { item ->

        }
    }
}