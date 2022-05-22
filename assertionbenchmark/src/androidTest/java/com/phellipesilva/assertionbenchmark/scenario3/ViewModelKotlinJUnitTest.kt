package com.phellipesilva.assertionbenchmark.scenario3

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import app.cash.turbine.test
import com.phellipesilva.benchmarkable.scenario3.ViewModel
import com.phellipesilva.benchmarkable.scenario3.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario3.repository.FakeRepository
import org.junit.Rule
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ViewModelKotlinJUnitTest {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val fakeRepository = FakeRepository()
    private val viewModel = ViewModel(fakeRepository)

    @Test
    fun showLoadingWhenStartingFetchingOperationAndShowUsersWhenFinish() = runTest {
        fakeRepository.setUsersResponse(listOf("User1", "User2"))

        viewModel.viewState.test {
            viewModel.fetchUsers()

            val viewState1 = awaitItem()
            val viewState2 = awaitItem()

            benchmarkRule.measureRepeated {
                assertTrue(actual = viewState1.isLoading)
                assertEquals(
                    actual = viewState2, expected = ViewState(
                        isLoading = false,
                        showError = false,
                        users = listOf("User1", "User2")
                    )
                )
            }
        }
    }

    @Test
    fun showErrorWhenFetchingUserOperationFinishesWithError() = runTest {
        fakeRepository.enforceErrorOnFetchingOperation()

        viewModel.viewState.test {
            viewModel.fetchUsers()

            val viewState1 = awaitItem()
            val viewState2 = awaitItem()

            benchmarkRule.measureRepeated {
                assertTrue(actual = viewState1.isLoading)
                assertEquals(
                    actual = viewState2, expected = ViewState(
                        isLoading = false,
                        showError = true,
                        users = listOf()
                    )
                )
            }
        }
    }
}