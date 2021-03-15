package scenario3

import app.cash.turbine.test
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import scenario3.repository.FakeRepository
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ViewModelHamkrestTest {

    private val fakeRepository = FakeRepository()
    private val viewModel = ViewModel(fakeRepository)

    private fun isTrue(boolean: Boolean) = boolean
    private val isTrueMatcher = Matcher(::isTrue)

    @Test
    fun `Show loading when starting fetching operation and show users when finish`() = runBlockingTest {
        fakeRepository.setUsersResponse(listOf("User1", "User2"))

        viewModel.viewState.test {
            viewModel.fetchUsers()

            assertThat(expectItem().isLoading, isTrueMatcher)
            assertThat(expectItem(), equalTo(
                ViewState(
                    isLoading = false,
                    showError = false,
                    users = listOf("User1", "User2")
                )
            ))
        }
    }

    @Test
    fun `Show error when fetching user operation finishes with error`() = runBlockingTest {
        fakeRepository.enforceErrorOnFetchingOperation()

        viewModel.viewState.test {
            viewModel.fetchUsers()

            assertThat(expectItem().isLoading, isTrueMatcher)
            assertThat(expectItem(), equalTo(
                ViewState(
                    isLoading = false,
                    showError = true,
                    users = listOf()
                )
            ))
        }
    }
}

