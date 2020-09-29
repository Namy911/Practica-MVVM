package com.example.practica.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.practica.R
import com.example.practica.data.entity.Article
import com.example.practica.data.entity.Category
import com.example.practica.data.entity.CategoryAndArticle
import com.example.practica.databinding.ListRowEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.sppiner_row_category.view.*


@AndroidEntryPoint
class EditArticleFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        private const val TAG = "EditArticleFragment"
        private const val MODEL_CATEGORY_ARTICLE = "ui.main.model.category.article"
        private const val ARTICLE_ID = "ui.mai.article.id"

        fun newInstance(model: CategoryAndArticle?) = EditArticleFragment().apply {
            arguments = bundleOf(MODEL_CATEGORY_ARTICLE  to model)
        }
    }

    private var model: CategoryAndArticle? = null
    private lateinit var category: Category

    private lateinit var binding: ListRowEditBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        ListRowEditBinding.inflate(layoutInflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = arguments?.getParcelable(MODEL_CATEGORY_ARTICLE)

        // Set spinner with all categories
        viewModel.categories.observe(viewLifecycleOwner) { list ->
            binding.spinCategory.adapter = SpinnerCategoryAdapter(requireContext(), list)
            // Select category from article lis
            if (model != null) {
                val pos = list.indexOf(model?.category)
                binding.spinCategory.setSelection(pos)
            }

        }
        binding.spinCategory.onItemSelectedListener = this

        // Set article data from update View: name, title, desc, image
        // If model exist display  update View, else insert View
        if (model != null) { binding.model = model }

        // Select image
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                binding.imgSelect.setImageURI(uri)
            }
        binding.btnImage.setOnClickListener { getContent.launch("image/*") }

        // Bottom navigation save and redirect, save an clear input to add new article
        binding.bottNav?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bot_menu_save -> {
                    viewModel.saveArticle(getArticle());
                    requireActivity().supportFragmentManager.commit {
                        replace(R.id.container, MainFragment.newInstance())
                    }
                    true
                }
                R.id.bot_menu_save_new -> {
                    Log.d(TAG, "onViewCreated: ${getArticle()}"); true
                }
                else -> {
                    false
                }
            }
        }
    }

    // Setup Article model
    private fun getArticle() = Article(
        binding.edtTitle.text.toString(),
        binding.edtDesc.text.toString(),
        binding.edtContent.text.toString(),
        1,
        category.id,
        binding.model!!.article[0].id
    )

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category = parent?.getItemAtPosition(position) as Category
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    // Create list of all categories in spinner
    inner class SpinnerCategoryAdapter(context: Context, items: List<Category>) :
        ArrayAdapter<Category>(context, 0, items) {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return this.createView(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return this.createView(position, convertView, parent)
        }

        // Create view and associate data
        private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
            val category = getItem(position)
            val view = recycledView ?: LayoutInflater.from(context)
                .inflate(R.layout.sppiner_row_category, parent, false)
            view.txt_category.text = category!!.name
            return view
        }
    }
}