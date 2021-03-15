package scenario3

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import scenario3.repository.FakeRepository
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ViewModelKotlinJUnitTest {

    private val fakeRepository = FakeRepository()
    private val viewModel = ViewModel(fakeRepository)

    @Test
    fun `Show loading when starting fetching operation and show users when finish`() = runBlockingTest {
        fakeRepository.setUsersResponse(listOf("User1", "User2"))

        viewModel.viewState.test {
            viewModel.fetchUsers()

            assertTrue(actual = expectItem().isLoading)
            assertEquals(actual = expectItem(), expected = ViewState(
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

            assertTrue(actual = expectItem().isLoading)
            assertEquals(actual = expectItem(), expected = ViewState(
                isLoading = false,
                showError = true,
                users = listOf()
            ))
        }
    }
}