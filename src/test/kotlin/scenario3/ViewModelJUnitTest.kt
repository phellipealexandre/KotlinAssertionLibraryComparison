package scenario3

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import scenario3.repository.FakeRepository
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ViewModelJUnitTest {

    private val fakeRepository = FakeRepository()
    private val viewModel = ViewModel(fakeRepository)

    @Test
    fun `Show loading when starting fetching operation and show users when finish`() = runBlockingTest {
        fakeRepository.setUsersResponse(listOf("User1", "User2"))

        viewModel.viewState.test {
            viewModel.fetchUsers()

            assertEquals(expectItem().isLoading, true)
            assertEquals(expectItem(), ViewState(
                isLoading = false,
                showError = false,
                users = listOf("User1", "User2")
            ))
        }
    }

    @Test
    fun `Show error when fetching user operation finishes with error`() = runBlockingTest {
        fakeRepository.enforceErrorOnFetchingOperation()

        viewModel.viewState.test {
            viewModel.fetchUsers()

            assertEquals(expectItem().isLoading, true)
            assertEquals(expectItem(), ViewState(
                isLoading = false,
                showError = true,
                users = listOf()
            ))
        }
    }
}