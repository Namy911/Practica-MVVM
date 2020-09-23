package com.example.practica.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.practica.databinding.ListRowEditBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditArticleFragment : Fragment() {
    companion object {
        private const val TAG = "EditArticleFragment"
        private const val ARTICLE_ID = "ui.mai.article_id"
        private const val USER_ID = "ui.mai.user_id"
        private const val CATEGORY_ID = "ui.mai.category_id"

        fun newInstance(articleId: Int) = EditArticleFragment().apply {
            arguments = bundleOf(ARTICLE_ID to articleId)
        }
    }

    private var articleId: Int = -1
    private lateinit var binding: ListRowEditBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ListRowEditBinding.inflate(layoutInflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleId = requireArguments().getInt(ARTICLE_ID, -1)
//        viewModel.loadArticle(articleId)
//        viewModel.article.observe(viewLifecycleOwner, Observer { binding.model = it })

        viewModel.loadArticleToEdit(articleId)
        viewModel.articleToEdit.observe(viewLifecycleOwner, Observer { binding.model = it })
    }
}