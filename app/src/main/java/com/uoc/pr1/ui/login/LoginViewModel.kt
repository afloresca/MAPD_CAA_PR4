package com.uoc.pr1.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.uoc.pr1.data.LoginRepository
import com.uoc.pr1.data.Result

import com.uoc.pr1.R
import com.uoc.pr1.data.ListenerData

class LoginViewModel(val loginRepository: LoginRepository) : ViewModel() {

    private var _loginForm = MutableLiveData<LoginFormState>()
    var loginFormState: LiveData<LoginFormState> = _loginForm

    private var _loginResult = MutableLiveData<LoginResult>()
    var loginResult: LiveData<LoginResult> = _loginResult

    private var listener = ListenerData()

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        listener.onLogin = { result ->
            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName,result.data.userId))

            }
            else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }

        }


        val result = loginRepository.loginAsync(username, password,listener)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}