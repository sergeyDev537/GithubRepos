package com.example.githubrepos.domain.usecases

import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.domain.repositories.UserRepository

class GetRepoUseCase(private val userRepository: UserRepository) {

    operator fun invoke(id: Int): RepoEntity {
        return userRepository.getItemRepository(id)
    }

}