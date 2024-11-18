package com.example.githubrepos.presentation.ui.downloaded_repos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.githubrepos.R
import com.example.githubrepos.databinding.ItemDownloadRepoBinding
import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.presentation.ui.base.ReposDiffCallback
import com.example.githubrepos.utils.loadImage

class DownloadedReposAdapter : ListAdapter<RepoEntity, DownloadedRepoViewHolder>(ReposDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadedRepoViewHolder {
        val binding = ItemDownloadRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DownloadedRepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DownloadedRepoViewHolder, position: Int) {
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
    }
}