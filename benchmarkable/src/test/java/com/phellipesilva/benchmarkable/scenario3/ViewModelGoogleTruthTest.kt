package com.phellipesilva.benchmarkable.scenario3

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario3.repository.FakeRepository
import kotlinx.coroutines.test.runTest
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ViewModelGoogleTruthTest {

    private val fakeRepository = FakeRepository()
    private val viewModel = ViewModel(fakeRepository)

    @Test
    fun `Show loading when starting fetching operation and show users when finish`() = runTest {
        fakeRepository.setUsersResponse(listOf("User1", "User2"))

        viewModel.viewState.test {
            viewModel.fetchUsers()

            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem()).isEqualTo(
                ViewState(
                    isLoading = false,
                    showError = false,
                    users = listOf("User1", "User2")
                )
            )
        }
    }

    @Test
    fun `Show error when fetching user operation finishes with error`() = runTest {
        fakeRepository.enforceErrorOnFetchingOperation()

        viewModel.viewState.test {
            viewModel.fetchUsers()

            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem()).isEqualTo(
                ViewState(
                    isLoading = false,
                    showError = true,
                    users = listOf()
                )
            )
        }
    }
}

