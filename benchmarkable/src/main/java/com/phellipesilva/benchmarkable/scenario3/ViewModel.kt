package com.phellipesilva.benchmarkable.scenario3

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.phellipesilva.benchmarkable.scenario3.repository.Repository

/**
 * This is the system under test:
 * Here we have a common component used in Android codebases with MVVM architecture.
 *
 * Use case 1 - Start loading: Show loading when starting user fetching operation. Hide loading when operation finishes.
 * Use case 2 - Success on fetch users: Show list of users when fetching is successful.
 * Use case 3 - Error on fetch users: Show error when fetching fails.
 */
class ViewModel(private val repository: Repository) {

    private val _viewState = MutableStateFlow(ViewState(isLoading = true))
    val viewState: StateFlow<ViewState> = _viewState

    suspend fun fetchUsers() {
        try {
            val users = repository.getUsers()
            _viewState.value = _viewState.value.copy(isLoading = false, users = users)
        } catch (exception: Exception) {
            _viewState.value = _viewState.value.copy(isLoading = false, showError = true)
        }
    }
}