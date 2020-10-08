package com.example.practica.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.practica.R
import com.example.practica.data.entity.Article
import com.example.practica.data.entity.Category
import com.example.practica.data.entity.CategoryAndArticle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_row_edit.*
import kotlinx.android.synthetic.main.sppiner_row_category.view.*
import java.util.*


@AndroidEntryPoint
class EditArticleFragment : Fragment(R.layout.list_row_edit), AdapterView.OnItemSelectedListener {
    companion object {
        private const val TAG = "EditArticleFragment"
        private const val BUNDLE_SPINNER = "ui.main.spinner.pos"
    }

    private lateinit var category: Category
    private var categoryPos: Int? = null

    private var imageRes: Uri? = null

    private var model: CategoryAndArticle? = null
    private val args: EditArticleFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = args.model

        // Set spinner with all categories
        viewModel.categories.observe(viewLifecycleOwner) { list ->
            spin_category.adapter = SpinnerCategoryAdapter(requireContext(), list)
            // Set default category from update view
            if (model != null) {
                val pos = list.indexOf(model?.category)
                spin_category.setSelection(pos)
//                    binding.spinCategory.setSelection(categoryPos!!)
            }
            // Save state on change config from spinner in save view
            if (savedInstanceState != null) {
                spin_category.setSelection(savedInstanceState.getInt(BUNDLE_SPINNER))
            }
        }
        spin_category.onItemSelectedListener = this

        // Set article data from update View: name, title, desc, image
        // If model exist display  update View with data, else with View empty fields
        if (model != null) {
            val article = model!!.article[0]
            edt_title.setText(article.title)
            edt_desc.setText(article.desc)
            edt_content.setText(article.content)
            img_select.setImageURI(article.img)
            // Delete item menu if display update view
            bottom_nav.menu.removeItem(R.id.bot_menu_save_new)
        }

        // Select image
        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    img_select.setImageURI(uri)
                    imageRes = uri
                }
            }
        btn_image.setOnClickListener { getContent.launch("image/*") }


//        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val intent = result.data
//                intent?.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
//                img_select.setImageURI(intent?.data)
//                imageRes = intent?.data
//            }
//        }
//        btn_image.setOnClickListener { startForResult.launch(Intent(Intent.ACTION_PICK,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)) }

        // Bottom navigation save and redirect, save an clear input to add new article
        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bot_menu_save -> { saveArticle() }
                R.id.bot_menu_save_new -> { saveArticle(false) }
                else -> { false }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(BUNDLE_SPINNER, categoryPos!!)
    }

    // Save article and redirect or clear input to add new article
    private fun saveArticle(redirect: Boolean = true): Boolean {
        if (validation()) {
            val article = getArticle()
            viewModel.saveArticle(article)
            if (redirect) {
                redirectTo()
            }
            else {
                edt_title.text.clear()
                edt_desc.text.clear()
                edt_content.text.clear()
                img_select.setImageDrawable(resources.getDrawable(R.drawable.l11))//Default image
            }
            Toast.makeText(context, getString(R.string.msg_save_article, article.title), Toast.LENGTH_SHORT).show()
            return true
        } else {
            return false
        }
    }

    private fun redirectTo() { findNavController().popBackStack() }

    // Setup Article model null = save, null != update
    private fun getArticle(): Article {
        return if(model != null) {
            val model = model!!.article[0]
            model.copy(
                title = edt_title.text.toString(),
                desc = edt_desc.text.toString(),
                img = imageRes,
                content = edt_content.text.toString(),
                categoryId = category.id
            )
        }else{
            Article(
                edt_title.text.toString(),
                edt_desc.text.toString(),
                imageRes,
                Date(System.currentTimeMillis()),
                edt_content.text.toString(),
                1,
                category.id
            )
        }
    }
    // Check if inputs is completed
    private fun validation(): Boolean{
        if (edt_title.text.isEmpty()){ edt_title.error = getString(R.string.empty_filed) }
        if (edt_desc.text.isEmpty()){ edt_desc.error = getString(R.string.empty_filed) }
        if (edt_content.text.isEmpty()){ edt_content.error = getString(R.string.empty_filed) }

        return  (edt_title.text.isNotEmpty() && edt_desc.text.isNotEmpty() && edt_content.text.isNotEmpty())
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category = parent?.getItemAtPosition(position) as Category // from setup model Article -> categoryId
        categoryPos = position // from spinner: position from selected category on update and  restore on conf. change
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