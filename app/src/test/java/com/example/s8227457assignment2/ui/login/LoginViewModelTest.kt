package com.example.s8227457assignment2.ui.login

import com.example.s8227457assignment2.data.model.LoginResponse
import com.example.s8227457assignment2.data.repository.InvestmentRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: InvestmentRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()

        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success updates state to Success`() = runTest {

        coEvery {
            repository.login("8227457", "Sohil")
        } returns Response.success(
            LoginResponse("testKeypass")
        )

        viewModel.login(
            username = "8227457",
            password = "Sohil"
        )

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            LoginState.Success("testKeypass"),
            viewModel.loginState.value
        )
    }

    @Test
    fun `blank username returns error`() = runTest {

        viewModel.login(
            username = "",
            password = "Sohil"
        )

        assertEquals(
            LoginState.Error(
                "Username and password are required."
            ),
            viewModel.loginState.value
        )
    }
}