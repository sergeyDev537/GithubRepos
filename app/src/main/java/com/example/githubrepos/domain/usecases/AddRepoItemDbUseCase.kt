package com.example.githubrepos.domain.usecases

import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.domain.repositories.UserRepository

class AddRepoItemDbUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(repoEntity: RepoEntity) {
        userRepository.addDownloadedRepository(repoEntity)
    }

}