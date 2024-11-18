package com.example.githubrepos.domain.repositories

import com.example.githubrepos.domain.entities.RepoEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getListRepository(username: String): List<RepoEntity>

    fun getDownloadedListRepository(): Flow<List<RepoEntity>>

    suspend fun downloadRepositoryUseCase(repoId: Int): Long

    suspend fun addDownloadedRepository(repoId: Int)

}