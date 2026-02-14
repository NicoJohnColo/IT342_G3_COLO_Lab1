package com.g3.bookbud.data.model

data class UserProfile(
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean,
    val createdAt: String?,
    val updatedAt: String?,
    val lastLogin: String?
)