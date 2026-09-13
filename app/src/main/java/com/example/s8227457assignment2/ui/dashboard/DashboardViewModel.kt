package com.example.s8227457assignment2.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8227457assignment2.data.model.Investment
import com.example.s8227457assignment2.data.repository.InvestmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: InvestmentRepository
) : ViewModel() {

    private val _dashboardState =
        MutableStateFlow<DashboardState>(DashboardState.Idle)

    val dashboardState: StateFlow<DashboardState> =
        _dashboardState.asStateFlow()

    fun loadDashboard(keypass: String) {

        if (keypass.isBlank()) {
            _dashboardState.value =
                DashboardState.Error("Missing dashboard key.")
            return
        }

        viewModelScope.launch {

            _dashboardState.value = DashboardState.Loading

            try {
                val response =
                    repository.getDashboard(keypass)

                if (response.isSuccessful) {

                    val dashboardResponse = response.body()

                    if (dashboardResponse != null) {

                        _dashboardState.value =
                            DashboardState.Success(
                                investments = dashboardResponse.entities,
                                entityTotal = dashboardResponse.entityTotal
                            )

                    } else {
                        _dashboardState.value =
                            DashboardState.Error(
                                "Empty dashboard response."
                            )
                    }

                } else {
                    _dashboardState.value =
                        DashboardState.Error(
                            "Unable to load dashboard."
                        )
                }

            } catch (e: Exception) {
                _dashboardState.value =
                    DashboardState.Error(
                        e.message ?: "Unable to connect to the server."
                    )
            }
        }
    }
}

sealed class DashboardState {

    data object Idle : DashboardState()

    data object Loading : DashboardState()

    data class Success(
        val investments: List<Investment>,
        val entityTotal: Int
    ) : DashboardState()

    data class Error(
        val message: String
    ) : DashboardState()
}