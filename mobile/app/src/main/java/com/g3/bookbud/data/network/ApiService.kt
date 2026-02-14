package com.example.colo.mini_app.data.network

import com.example.colo.mini_app.data.model.JwtResponse
import com.example.colo.mini_app.data.model.LoginRequest
import com.example.colo.mini_app.data.model.RegisterRequest
import com.example.colo.mini_app.data.model.UserProfile
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ─────────────────────────────────────────────
    // Matches: AuthController.java
    // @PostMapping("/register")  →  /api/auth/register
    // ─────────────────────────────────────────────
    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<JwtResponse>

    // ─────────────────────────────────────────────
    // Matches: AuthController.java
    // @PostMapping("/login")  →  /api/auth/login
    // ─────────────────────────────────────────────
    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<JwtResponse>

    // ─────────────────────────────────────────────
    // Matches: UserController.java
    // @GetMapping("/me")  →  /api/user/me
    // Requires JWT token in Authorization header
    // ─────────────────────────────────────────────
    @GET("api/user/me")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<UserProfile>
}