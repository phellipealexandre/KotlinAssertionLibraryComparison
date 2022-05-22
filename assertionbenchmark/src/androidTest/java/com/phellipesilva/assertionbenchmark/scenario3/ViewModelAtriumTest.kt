package com.phellipesilva.assertionbenchmark.scenario3

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import app.cash.turbine.test
import ch.tutteli.atrium.api.fluent.en_GB.feature
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import com.phellipesilva.benchmarkable.scenario3.ViewModel
import com.phellipesilva.benchmarkable.scenario3.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario3.repository.FakeRepository
import org.junit.Rule
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ViewModelAtriumTest {

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
                expect(viewState1).feature({ f(it::isLoading) }) { toBe(true) }
                expect(viewState2) {
                    toBe(
                        ViewState(
                            isLoading = false,
                            showError = false,
                            users = listOf("User1", "User2")
                        )
                    )
                }
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
                expect(viewState1.isLoading) { toBe(true) }
                expect(viewState2) {
                    toBe(
                        ViewState(
                            isLoading = false,
                            showError = true,
                            users = listOf()
                        )
                    )
                }
            }
        }
    }
}