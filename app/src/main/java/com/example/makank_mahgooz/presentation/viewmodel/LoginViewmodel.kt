package com.example.makank_mahgooz.presentation.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makank_mahgooz.data.model.LoginRequest
import com.example.makank_mahgooz.data.model.LoginResponse
import com.example.makank_mahgooz.data.networking.ApiServices
import com.example.makank_mahgooz.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor( val apiServices: ApiServices,  val authInterceptor: AuthInterceptor) : ViewModel()
{
    private val _loginRespose = MutableStateFlow<LoginResponse?>(null)
    val loginResponse : StateFlow<LoginResponse?> get() = _loginRespose

    fun login(email: String, password: String , onLoginSuccess:()->Unit, onLoginFalier: ()->Unit) {
        viewModelScope.launch {
            val response = apiServices.login(LoginRequest(email = email, password = password))
            if (response.isSuccessful) {
                _loginRespose.value = response.body()
                authInterceptor.setToken(response.body()?.token?:"")
                response.body()?.token
                onLoginSuccess()
                onLoginFalier()

            }
        }
    }
}