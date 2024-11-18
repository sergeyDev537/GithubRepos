package com.example.githubrepos.presentation.ui.downloaded_repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.domain.usecases.GetDownloadedReposUseCase
import com.example.githubrepos.presentation.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DownloadedReposViewModel @Inject constructor(
    private val getDownloadedReposUseCase: GetDownloadedReposUseCase,
) : ViewModel() {

    val uiState = getDownloadedReposUseCase()
        .map { repos ->
            val state = if (repos.isNotEmpty()) { UiState.Success(repos) } else { UiState.SuccessEmptyList }
            state
        }
        .onStart { emit(UiState.Loading) }
        .catch { emit(UiState.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState.Initial
        )
}