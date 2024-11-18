package com.example.githubrepos.presentation.ui.base

import androidx.recyclerview.widget.DiffUtil
import com.example.githubrepos.domain.entities.RepoEntity

class ReposDiffCallback : DiffUtil.ItemCallback<RepoEntity>() {

    override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean {
        return oldItem == newItem
    }

}