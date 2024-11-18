package com.example.githubrepos.presentation.ui.search

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.githubrepos.R
import com.example.githubrepos.databinding.FragmentSearchBinding
import com.example.githubrepos.presentation.ui.base.BaseFragment
import com.example.githubrepos.presentation.ui.search.adapter.SearchRepoAdapter
import com.example.githubrepos.presentation.ui.state.UiState
import com.example.githubrepos.utils.openUrl
import com.example.githubrepos.utils.showError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    private val reposAdapter by lazy { SearchRepoAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeUI()
    }

    private fun setupUI() {
        setupAdapter()
        binding.buttonSearch.setOnClickListener {
            val username = binding.etUsername.text.toString()
            viewModel.getRepos(username)
        }
    }

    private fun setupAdapter() {
        reposAdapter.openWebRepo = {
            requireContext().openUrl(url = it)
        }
        reposAdapter.downloadRepo = { repoId ->
            viewModel.updateRepoId(repoId = repoId)

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                checkPermissions()
            } else {
                viewModel.downloadRepo(repoId = repoId)
            }
        }
        binding.rvUserRepositories.adapter = reposAdapter
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
            rvUserRepositories.isVisible = isSuccess
            textEmptyRepos.isVisible = isEmptyRepos
        }
    }

    private fun checkPermissions() {
        activityResultLauncher.launch(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                viewModel.downloadRepo(repoId = null)
            } else {
                binding.root.showError(getString(R.string.permission_not_granted))
            }
        }
}