package com.example.githubrepos.domain.usecases

import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.domain.repositories.UserRepository

class GetDownloadedReposUseCase(private val userRepository: UserRepository) {

    operator fun invoke(): List<RepoEntity> {
        return userRepository.getDownloadedListRepository()
    }

}