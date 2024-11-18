package com.example.githubrepos.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.domain.usecases.AddRepoItemDbUseCase
import com.example.githubrepos.domain.usecases.DownloadRepoUseCase
import com.example.githubrepos.domain.usecases.GetReposUseCase
import com.example.githubrepos.presentation.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase,
    private val downloadRepoUseCase: DownloadRepoUseCase,
    private val addRepoItemDbUseCase: AddRepoItemDbUseCase,
) : ViewModel() {

    val uiState = MutableStateFlow<UiState>(UiState.Initial)
    private var localRepoId: Int? = null
    private var jobRepos: Job? = null
    private var jobDownload: Job? = null

    fun getRepos(username: String) {
        if (isValidUsername(username)) {
            uiState.value = UiState.Loading

            jobRepos?.cancel()
            jobRepos = viewModelScope.launch {
                val result = getReposUseCase(username = username)

                if (result.isEmpty()) {
                    uiState.value = UiState.SuccessEmptyList
                } else {
                    uiState.value = UiState.Success(result)
                }
            }
        } else {
            uiState.value = UiState.Error(Throwable(message = "Enter correct username"))
        }

    }

    private fun isValidUsername(username: String): Boolean {
        return username.isNotEmpty()
    }

    fun updateRepoId(repoId: Int) {
        localRepoId = repoId
    }

    fun downloadRepo(repoId: Int?) {
        val currentRepoId = repoId ?: localRepoId ?: return
        jobDownload?.cancel()
        jobDownload = viewModelScope.launch {
            val downloadedId = downloadRepoUseCase.invoke(repoId = currentRepoId)
            addRepoItemDbUseCase.invoke(repoId = currentRepoId)
        }
    }
}