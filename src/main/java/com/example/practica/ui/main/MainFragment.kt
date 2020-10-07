package com.example.practica.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practica.R
import com.example.practica.data.entity.Article
import com.example.practica.data.entity.UserAndArticle
import com.example.practica.databinding.ListRowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    companion object {
        private const val TAG = "MainFragment"
    }

    private val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RosterAdapter(){
            findNavController().navigate(
                MainFragmentDirections.actionDisplayArticle(it)
            )
        }

        list_container.adapter = adapter
        viewModel.listUsersAndArticle.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list.sortedBy { it.article[0].id })
            if (list.isEmpty()){
                txt_empty_list.visibility = View.VISIBLE
            }
        }

        fb_add_article.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionAddArticle(null)
            )
        }
    }

    inner class RosterAdapter(private val action: (Article) -> Unit): ListAdapter<UserAndArticle, RosterHolder>(Diff()){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterHolder {
            return RosterHolder(ListRowBinding.inflate(layoutInflater, parent, false),action)
        }

        override fun onBindViewHolder(holder: RosterHolder, position: Int) {
            return holder.bind(getItem(position))
        }

    }
    inner class Diff: DiffUtil.ItemCallback<UserAndArticle>(){
        override fun areItemsTheSame(oldItem: UserAndArticle, newItem: UserAndArticle) =
             oldItem.article[0].id == newItem.article[0].id &&
                    oldItem.user.id == newItem.user.id
        
        override fun areContentsTheSame(oldItem: UserAndArticle, newItem: UserAndArticle) =
            oldItem == newItem

    }
    inner class RosterHolder(private val binding: ListRowBinding, val action: (Article) -> Unit): RecyclerView.ViewHolder(binding.root){
            fun bind(model: UserAndArticle){
                binding.model = model
                binding.txtDate.text = SimpleDateFormat("EEE, dd MMM ''yy", Locale.getDefault()).format(model.article[0].date)
//                binding.imgArticle.setImageResource(Uri.parse(model.article[0].img)
                binding.holder = this
                binding.executePendingBindings()
            }
    }
}