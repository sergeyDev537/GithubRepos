package com.example.githubrepos.domain.usecases

import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.domain.repositories.UserRepository

class DownloadRepoUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(repoEntity: RepoEntity): Long {
        return userRepository.downloadRepositoryUseCase(repoEntity)
    }

}