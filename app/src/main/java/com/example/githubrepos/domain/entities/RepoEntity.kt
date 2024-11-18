package com.example.githubrepos.domain.entities

data class RepoEntity(
    val archiveUrl: String,
    val cloneUrl: String,
    val description: String,
    val fullName: String,
    val gitUrl: String,
    val htmlUrl: String,
    val id: Int,
    val language: String,
    val name: String,
    val nodeId: String,
    val owner: OwnerEntity,
    val isPrivate: Boolean,
    val sshUrl: String,
    val url: String,
    val defaultBranch: String
)