package com.example.s8227457assignment2.ui.dashboard

import com.example.s8227457assignment2.data.model.DashboardResponse
import com.example.s8227457assignment2.data.model.Investment
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
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: InvestmentRepository
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()

        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `dashboard success updates state with investments`() = runTest {

        val investments = listOf(
            Investment(
                assetType = "Stock",
                ticker = "AAPL",
                currentPrice = 150.25,
                dividendYield = 0.65,
                description = "Apple investment"
            )
        )

        coEvery {
            repository.getDashboard("testKeypass")
        } returns Response.success(
            DashboardResponse(
                entities = investments,
                entityTotal = 1
            )
        )

        viewModel.loadDashboard("testKeypass")

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            DashboardState.Success(
                investments = investments,
                entityTotal = 1
            ),
            viewModel.dashboardState.value
        )
    }

    @Test
    fun `blank keypass returns error`() = runTest {

        viewModel.loadDashboard("")

        assertEquals(
            DashboardState.Error(
                "Missing dashboard key."
            ),
            viewModel.dashboardState.value
        )
    }
}