package com.example.practica.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.practica.R
import com.example.practica.data.entity.Article
import com.example.practica.data.entity.CategoryAndArticle
import com.example.practica.databinding.DispayRowBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DisplayArticleFragment: Fragment() {

    companion object {
        const val ARTICLE_MODEL = "ui.main.article.model"
        const val CATEGORY_ID = "ui.main.category.id"

        fun newInstance(article: Article) = DisplayArticleFragment()
            .apply { arguments = bundleOf(ARTICLE_MODEL to article) }
    }
    lateinit var binding: DispayRowBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        DispayRowBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("EditArticleFragment", "onViewCreated: ${resources.getIdentifier("l11", "drawable", context?.packageName)}")
        val article = requireArguments().getParcelable<Article>(ARTICLE_MODEL)
        viewModel.laodCategory(article!!.categoryId)
        viewModel.category.observe(viewLifecycleOwner, { binding.model = CategoryAndArticle(it, listOf(article)) })

        binding.txtDate.text = SimpleDateFormat("EEE, dd MMM ''yy", Locale.getDefault()).format(article.date)

        binding.botNavDisplay.setOnNavigationItemSelectedListener{ item ->
            when(item.itemId){
                R.id.bot_menu_update -> {updateArticle(); true}
                R.id.bot_menu_delete -> {deleteArticle(); true}
                else -> { false}
            }

        }

    }
    // Delete an redirect to main screen
    private fun deleteArticle(){
        viewModel.deleteArticle(binding.model!!.article[0])
        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container_display, MainFragment.newInstance())
        }
    }

    private fun updateArticle(){
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container_display, EditArticleFragment.newInstance(binding.model!!))
        }
    }
}
