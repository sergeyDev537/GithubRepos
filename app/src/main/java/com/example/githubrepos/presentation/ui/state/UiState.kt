package com.example.githubrepos.presentation.ui.state

import com.example.githubrepos.domain.entities.RepoEntity

sealed class UiState {
    data object Loading : UiState()
    data class Success(val repos: List<RepoEntity>) : UiState()
    data object SuccessEmptyList : UiState()
    data class Error(val throwable: Throwable) : UiState()
    data object Initial : UiState()
}