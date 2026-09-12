package com.example.s8227457assignment2.data.repository

import com.example.s8227457assignment2.data.model.DashboardResponse
import com.example.s8227457assignment2.data.model.LoginRequest
import com.example.s8227457assignment2.data.model.LoginResponse
import com.example.s8227457assignment2.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class InvestmentRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(
        username: String,
        password: String
    ): Response<LoginResponse> {

        val loginRequest = LoginRequest(
            username = username,
            password = password
        )

        return apiService.login(loginRequest)
    }

    suspend fun getDashboard(
        keypass: String
    ): Response<DashboardResponse> {
        return apiService.getDashboard(keypass)
    }
}