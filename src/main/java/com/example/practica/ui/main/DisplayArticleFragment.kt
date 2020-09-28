package com.example.practica.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.practica.R
import com.example.practica.databinding.DispayRowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayArticleFragment: Fragment() {

    companion object {
        const val CATEGORY_ARTICLE = "ui.main.article"
        fun newInstance(articleId: Int) = DisplayArticleFragment()
            .apply { arguments = bundleOf(CATEGORY_ARTICLE to articleId) }
    }
    lateinit var binding: DispayRowBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        DispayRowBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleId = requireArguments().getInt(CATEGORY_ARTICLE)
        viewModel.loadArticleToEdit(articleId)
        viewModel.articleToEdit.observe(viewLifecycleOwner, { binding.model = it })

        binding.botNavDisplay.setOnNavigationItemSelectedListener{ item ->
            when(item.itemId){
                R.id.bot_menu_update -> {updateArticle(); true}
                R.id.bot_menu_delete -> {true}
                else -> { false}
            }

        }

    }

    private fun updateArticle(){
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container, EditArticleFragment.newInstance(binding.model!!.article[0].id, binding.model!!.category))
        }
    }
}
