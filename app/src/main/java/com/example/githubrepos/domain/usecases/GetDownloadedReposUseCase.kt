package com.example.githubrepos.domain.usecases

import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDownloadedReposUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): Flow<List<RepoEntity>> {
        return userRepository.getDownloadedListRepository()
    }

}