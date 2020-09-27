package com.example.practica.ui.main

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import com.example.practica.R
import com.example.practica.data.entity.Article
import com.example.practica.data.entity.Category
import com.example.practica.databinding.ListRowEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_row_edit.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow


@AndroidEntryPoint
class EditArticleFragment : Fragment() {
    companion object {
        private const val TAG = "EditArticleFragment"
        private const val TAG_DIALOG = "CategoryDialog"
        private const val ARTICLE_ID = "ui.mai.article_id"

        fun newInstance(articleId: Int?) = EditArticleFragment().apply {
            arguments = bundleOf(ARTICLE_ID to articleId)
        }
    }

    private var articleId: Int = -1
    private lateinit var binding: ListRowEditBinding
    private val viewModel: MainViewModel by viewModels()

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ListRowEditBinding.inflate(layoutInflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(binding.model == null) {
            articleId = requireArguments().getInt(ARTICLE_ID, -1)
            viewModel.loadArticleToEdit(articleId)
            viewModel.articleToEdit.observe(viewLifecycleOwner, { binding.model = it })
        }

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? -> binding.imgSelect.setImageURI(uri) }
        binding.btnImage.setOnClickListener { getContent.launch("image/*") }


        binding.bottNav?.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.bot_menu_save -> { viewModel.saveArticle(getArticle()); true }
                R.id.bot_menu_save_new -> { CategoryDialog().show(childFragmentManager, TAG_DIALOG) ; true }
                else -> { false }
            }
        }
    }
    private fun getArticle() =
        Article(
            binding.edtTitle.text.toString(),
            binding.edtDesc.text.toString(),
            binding.edtContent.text.toString(),
            1,
            1
        )

    @AndroidEntryPoint
    class CategoryDialog: DialogFragment(){
        companion object{
            const val LIST_CATEGORIES = "ui.main.list.categories"
            fun newInstance(list: List<Category>) = CategoryDialog().apply {
                arguments = bundleOf(LIST_CATEGORIES to list)
            }
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val view: View = layoutInflater.inflate(R.layout.list_dialog, null)

            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setMessage("Salut")
                    .setMultiChoiceItems(R.array.dialog2, null,
                DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                })
//                    .setView(view)
                    .setPositiveButton("Da", {_,_ ->})
                builder.create()
            }?: throw IllegalStateException("Activity cannot be null")
        }

    }
    
}