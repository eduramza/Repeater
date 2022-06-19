package com.ramattec.repeater.ui.login

import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.login.EmailPasswordLoginUseCase
import com.ramattec.repeater.domain.login.GoogleSigInCredentialUseCase
import com.ramattec.repeater.domain.login.IsUserLoggedUseCase
import com.ramattec.repeater.domain.register.EmailValidateUseCase
import com.ramattec.repeater.domain.register.PasswordValidateUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var emailPasswordLoginUseCase: EmailPasswordLoginUseCase

    @MockK
    private lateinit var isUserLoggedUseCase: IsUserLoggedUseCase

    @MockK
    private lateinit var googleSigInCredentialUseCase: GoogleSigInCredentialUseCase

    @MockK
    private lateinit var emailValidateUseCase: EmailValidateUseCase

    @MockK
    private lateinit var passwordValidateUseCase: PasswordValidateUseCase

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(
            emailPasswordLoginUseCase,
            isUserLoggedUseCase,
            googleSigInCredentialUseCase,
            emailValidateUseCase,
            passwordValidateUseCase
        )
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `User Input, should change UI to email Input invalid when usecase return false`() =
        runTest {
            val email = ""
            every { emailValidateUseCase(any()) } returns false

//        val results = mutableListOf<LoginUIState>()

            viewModel.doLoginWithEmailAndPassword(email, "123")

//        val job = launch {
//            viewModel.uiState.toList(results)
//        }

            assertFalse(viewModel.uiState.value.isEmailInputValid)
//        job.cancel()
        }

    @Test
    fun `User Input, should change UI to email Input valid`() = runTest {
        val password = "SDA"
        every { emailValidateUseCase(any()) } returns true
        every { passwordValidateUseCase(any()) } returns false

        viewModel.doLoginWithEmailAndPassword("edmial@gmail.com", password)

        assertFalse(viewModel.uiState.value.isPasswordInputValid)
    }

    @Test
    fun `Email and Password Login, should change UI to loading and error`() = runTest {
        //setup
        val results = mutableListOf<LoginUIState>()
        val jobs = viewModel.uiState
            .onEach { results.add(it) }
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        val email = "email@gmail.com"
        val password = "123edauiawq31w3"
        every { emailValidateUseCase(any()) } returns true
        every { passwordValidateUseCase(any()) } returns true
        coEvery { emailPasswordLoginUseCase(any(), any()) } returns flow {
            emit(Outcome.Failure(IOException()))
        }


//        val job = launch {
//            viewModel.uiState.toList(results)
//        }
        viewModel.doLoginWithEmailAndPassword(email, password)
        runCurrent()

//        assertTrue(viewModel.uiState.value.isLoadingUser)
        assertNotNull(results.first().loginError)
        jobs.cancel()
    }
}
