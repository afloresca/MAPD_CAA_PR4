package com.uoc.pr1.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uoc.pr1.data.DataSource
import com.uoc.pr1.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class LoginViewModelFactory2(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}