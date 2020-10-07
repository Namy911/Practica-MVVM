package com.example.practica.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.practica.R
import com.example.practica.data.entity.CategoryAndArticle
import com.example.practica.databinding.DispayRowBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DisplayArticleFragment: Fragment() {

    private  val args: DisplayArticleFragmentArgs by navArgs()
    lateinit var binding: DispayRowBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        DispayRowBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article
        viewModel.laodCategory(article.categoryId)
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
        findNavController().popBackStack()
    }

    private fun updateArticle(){
        findNavController().navigate(
            DisplayArticleFragmentDirections.actionUpdateArticle(binding.model!!)
        )
    }
}
