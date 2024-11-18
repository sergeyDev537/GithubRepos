package com.example.githubrepos.domain.usecases

import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.domain.repositories.UserRepository

class GetReposUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(username: String): List<RepoEntity> {
        return userRepository.getListRepository(username)
    }

}