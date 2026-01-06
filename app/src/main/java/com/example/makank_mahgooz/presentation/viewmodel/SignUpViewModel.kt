package com.example.makank_mahgooz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makank_mahgooz.data.model.RegisterRequest
import com.example.makank_mahgooz.data.model.RegisterResponse
import com.example.makank_mahgooz.data.networking.ApiServices
import com.example.makank_mahgooz.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SignUpViewModel @Inject constructor(val  apiServices: ApiServices, val  authInterceptor: AuthInterceptor) : ViewModel()
{
    private val _RegisterRespone= MutableStateFlow<RegisterResponse?>(null)

    val registerResponse: StateFlow<RegisterResponse?> get() = _RegisterRespone



    fun register(firstName: String,lastName: String,Email: String, Password: String,onRegiserterSuccess:()->Unit ) {
        viewModelScope.launch {
            val response = apiServices.register(RegisterRequest( firstName = firstName,lastName = lastName,email = Email, password = Password))
            if (response.isSuccessful){
                _RegisterRespone.value = response.body()
               authInterceptor.setToken(response.body()?.token?:"")
                onRegiserterSuccess()
            }
        }

    }
}
