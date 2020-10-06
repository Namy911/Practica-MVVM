package com.example.practica.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
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
import java.util.*


@AndroidEntryPoint
class EditArticleFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object {
        private const val TAG = "EditArticleFragment"
        private const val MODEL_CATEGORY_ARTICLE = "ui.main.model.category.article"

        private const val BUNDLE_TITLE = "ui.main.title"
        private const val BUNDLE_DESC = "ui.main.desc"
        private const val BUNDLE_CONTENT = "ui.main.content"
        private const val BUNDLE_SPINNER = "ui.main.spinner.pos"

        fun newInstance(model: CategoryAndArticle?) = EditArticleFragment().apply {
            arguments = bundleOf(MODEL_CATEGORY_ARTICLE to model)
        }
    }

    private var model: CategoryAndArticle? = null
    private lateinit var category: Category
    private var categoryPos: Int? = null

    private lateinit var binding: ListRowEditBinding
    private val viewModel: MainViewModel by viewModels()
    private var imageRes: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ListRowEditBinding.inflate(layoutInflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = arguments?.getParcelable(MODEL_CATEGORY_ARTICLE)

        // Set spinner with all categories
        viewModel.categories.observe(viewLifecycleOwner) { list ->
            binding.spinCategory.adapter = SpinnerCategoryAdapter(requireContext(), list)
            // Select category from current article
            if (model != null) {
                // Save state on change config and set default category from update view
                if (savedInstanceState == null) {
                    val pos = list.indexOf(model?.category)
                    binding.spinCategory.setSelection(pos)
//                    binding.spinCategory.setSelection(categoryPos!!)
                } else {
                    binding.spinCategory.setSelection(savedInstanceState.getInt(BUNDLE_SPINNER))
                }
            }
            // Save state on change config from spinner in save view
            if (savedInstanceState != null) {
                binding.spinCategory.setSelection(savedInstanceState.getInt(BUNDLE_SPINNER))
            }
        }
        binding.spinCategory.onItemSelectedListener = this

        // Set article data from update View: name, title, desc, image
        // If model exist display  update View with data, else with View empty fields
        if (model != null) { binding.model = model }

            // Select image
            val getContent =
                registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                    binding.imgSelect.setImageURI(uri)
                    imageRes = Uri.parse(uri.toString())
                }
            binding.btnImage.setOnClickListener { getContent.launch("image/*") }

            // Bottom navigation save and redirect, save an clear input to add new article
            binding.bottNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.bot_menu_save -> {
                        viewModel.saveArticle(getArticle());
                        redirectTo();
                        true
                    }
                    R.id.bot_menu_save_new -> {
//                    Log.d(TAG, "onViewCreated: ${getArticle()}");
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            // Save state from update view, setup with article model inputs
            if(binding.model != null) {
                val categoryBundle = model!!.category
                val articleBundle = model!!.article[0]
                binding.model = CategoryAndArticle(
                    categoryBundle, listOf(
                        articleBundle.copy(
                            title = savedInstanceState.getString(BUNDLE_TITLE)!!,
                            desc = savedInstanceState.getString(BUNDLE_DESC)!!,
                            content = savedInstanceState.getString(BUNDLE_CONTENT)!!,
                        )
                    )
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(BUNDLE_TITLE, binding.edtTitle.text.toString())
        outState.putString(BUNDLE_DESC, binding.edtDesc.text.toString())
        outState.putString(BUNDLE_CONTENT, binding.edtContent.text.toString())
        outState.putInt(BUNDLE_SPINNER, categoryPos!!)
    }

    private fun redirectTo() {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container_display, MainFragment.newInstance())
        }
    }

    // Setup Article model null = save, null != update
    private fun getArticle(): Article {
        return if(model != null) {
            val model = binding.model!!.article[0]
            model.copy(
                title = binding.edtTitle.text.toString(),
                desc = binding.edtDesc.text.toString(),
                img = "2131230840",
                date = Date(1601640251700),
                content = binding.edtContent.text.toString(),
                userId = 1,
                categoryId = category.id
            )
        }else{
            Article(
                binding.edtTitle.text.toString(),
                binding.edtDesc.text.toString(),
                "2131230840",
                Date(System.currentTimeMillis()),
                binding.edtContent.text.toString(),
                1,
                category.id
            )
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category = parent?.getItemAtPosition(position) as Category
        categoryPos = position
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