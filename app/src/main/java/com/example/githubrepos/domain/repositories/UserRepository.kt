package com.example.githubrepos.domain.repositories

import com.example.githubrepos.domain.entities.RepoEntity

interface UserRepository {

    suspend fun getListRepository(username: String): List<RepoEntity>

    fun getDownloadedListRepository(): List<RepoEntity>

    suspend fun downloadRepositoryUseCase(repoEntity: RepoEntity): Long

    suspend fun addDownloadedRepository(repoEntity: RepoEntity)

    fun getItemRepository(id: Int): RepoEntity

}