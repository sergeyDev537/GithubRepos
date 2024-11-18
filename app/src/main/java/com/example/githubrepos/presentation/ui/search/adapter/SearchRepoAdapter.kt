package com.example.githubrepos.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.githubrepos.R
import com.example.githubrepos.databinding.ItemSearchRepoBinding
import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.presentation.ui.base.ReposDiffCallback
import com.example.githubrepos.utils.loadImage

class SearchRepoAdapter : ListAdapter<RepoEntity, SearchRepoViewHolder>(ReposDiffCallback()) {

    var openWebRepo: ((String) -> Unit)? = null
    var downloadRepo: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepoViewHolder {
        val binding = ItemSearchRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchRepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchRepoViewHolder, position: Int) {
        val repo = getItem(position)
        val binding = holder.binding
        val context = holder.binding.root.context

        with(binding.contentRepo) {
            imgUserPhoto.loadImage(repo.owner.avatarUrl)
            tvUsername.text = context.getString(R.string.username_label, repo.owner.login)
            tvNameRepo.text = context.getString(R.string.name_repository_label, repo.name)
            tvLanguage.text = context.getString(R.string.language_label, repo.language)
            tvBranch.text = context.getString(R.string.branch_label, repo.defaultBranch)
        }
        with(binding) {
            btnOpenWeb.setOnClickListener {
                openWebRepo?.invoke(repo.htmlUrl)
            }
            btnDownload.setOnClickListener {
                downloadRepo?.invoke(repo.id)
            }
        }
    }
}