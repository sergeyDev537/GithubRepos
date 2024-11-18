package com.example.githubrepos.presentation.ui.downloaded_repos

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.githubrepos.R
import com.example.githubrepos.databinding.FragmentDownloadedReposBinding
import com.example.githubrepos.presentation.ui.base.BaseFragment
import com.example.githubrepos.presentation.ui.downloaded_repos.adapter.DownloadedReposAdapter
import com.example.githubrepos.presentation.ui.state.UiState
import com.example.githubrepos.utils.showError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DownloadedReposFragment : BaseFragment<FragmentDownloadedReposBinding>(FragmentDownloadedReposBinding::inflate) {

    private val viewModel: DownloadedReposViewModel by viewModels()
    private val reposAdapter by lazy { DownloadedReposAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeUI()
    }

    private fun setupUI() {
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.rvUserRepositoriesDb.adapter = reposAdapter
    }

    private fun subscribeUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect { state ->
                    updateState(state)
                }
            }
        }
    }

    private fun updateState(state: UiState) {
        when (state) {
            is UiState.Loading -> {
                updateVisibilityContent(isLoading = true)
            }

            is UiState.Success -> {
                reposAdapter.submitList(state.repos)
                updateVisibilityContent(isSuccess = true)
            }

            is UiState.SuccessEmptyList -> {
                updateVisibilityContent(isEmptyRepos = true)
            }

            is UiState.Error -> {
                updateVisibilityContent(isError = true)
                binding.root.showError(message = state.throwable.message ?: getString(R.string.universal_error))
            }

            is UiState.Initial -> {}
        }
    }

    private fun updateVisibilityContent(
        isLoading: Boolean = false,
        isSuccess: Boolean = false,
        isError: Boolean = false,
        isEmptyRepos: Boolean = false,
    ) {
        with(binding) {
            pbRepos.isVisible = isLoading
            rvUserRepositoriesDb.isVisible = isSuccess
            textEmptyRepos.isVisible = isEmptyRepos
        }
    }
}