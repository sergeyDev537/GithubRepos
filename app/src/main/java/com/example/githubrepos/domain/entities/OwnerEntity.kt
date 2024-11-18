package com.example.githubrepos.domain.entities

data class OwnerEntity(
    val avatarUrl: String,
    val htmlUrl: String,
    val id: Int,
    val login: String,
    val nodeId: String,
    val type: String,
    val url: String
)