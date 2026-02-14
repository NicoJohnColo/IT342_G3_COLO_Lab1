package com.g3.bookbud.data.network

import com.g3.bookbud.data.model.JwtResponse
import com.g3.bookbud.data.model.LoginRequest
import com.g3.bookbud.data.model.RegisterRequest
import com.g3.bookbud.data.model.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/register")
    suspend fun register(
            @Body request: RegisterRequest
    ): Response<JwtResponse>

    @POST("api/auth/login")
    suspend fun login(
            @Body request: LoginRequest
    ): Response<JwtResponse>

    @GET("api/user/me")
    suspend fun getProfile(
            @Header("Authorization") token: String
    ): Response<UserProfile>
}