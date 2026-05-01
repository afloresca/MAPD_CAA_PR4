package com.uoc.pr1.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val userId: Int,
    val displayName: String
)