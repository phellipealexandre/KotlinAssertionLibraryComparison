package com.phellipesilva.benchmarkable.scenario3

import app.cash.turbine.test
import ch.tutteli.atrium.api.fluent.en_GB.feature
import ch.tutteli.atrium.api.fluent.en_GB.toBe
import ch.tutteli.atrium.api.verbs.expect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.phellipesilva.benchmarkable.scenario3.repository.FakeRepository
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ViewModelAtriumTest {

    private val fakeRepository = FakeRepository()
    private val viewModel = ViewModel(fakeRepository)

    @Test
    fun `Show loading when starting fetching operation and show users when finish`() = runTest {
        fakeRepository.setUsersResponse(listOf("User1", "User2"))

        viewModel.viewState.test {
            viewModel.fetchUsers()

            expect(awaitItem()).feature({ f(it::isLoading) }) { toBe(true) }
            expect(awaitItem()) {
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

    @Test
    fun `Show error when fetching user operation finishes with error`() = runTest {
        fakeRepository.enforceErrorOnFetchingOperation()

        viewModel.viewState.test {
            viewModel.fetchUsers()

            expect(awaitItem().isLoading) { toBe(true) }
            expect(awaitItem()) {
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