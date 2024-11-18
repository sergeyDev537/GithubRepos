package com.example.githubrepos.domain.usecases

import com.example.githubrepos.domain.repositories.UserRepository
import javax.inject.Inject

class DownloadRepoUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(repoId: Int): Long {
        return userRepository.downloadRepositoryUseCase(repoId)
    }

}