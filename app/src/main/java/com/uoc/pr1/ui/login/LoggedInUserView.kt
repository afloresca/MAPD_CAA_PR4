package com.uoc.pr1.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val userId: Int
    //... other data fields that may be accessible to the UI
)