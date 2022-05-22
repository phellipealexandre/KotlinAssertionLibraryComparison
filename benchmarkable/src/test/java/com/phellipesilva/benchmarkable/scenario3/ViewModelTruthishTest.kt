package com.phellipesilva.benchmarkable.scenario3

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario3.repository.FakeRepository
import com.varabyte.truthish.assertThat
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ViewModelTruthishTest {

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