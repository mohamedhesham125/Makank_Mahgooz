package com.example.makank_mahgooz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makank_mahgooz.data.model.Customer_inform
import com.example.makank_mahgooz.data.model.GetProfileResponse
import com.example.makank_mahgooz.data.networking.ApiServices
import com.example.makank_mahgooz.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewmodel @Inject constructor(
    val apiServices: ApiServices,
    val authInterceptor: AuthInterceptor
) : ViewModel() {

    private val _userRespone = MutableStateFlow<GetProfileResponse?>(null)
    val userRespone: StateFlow<GetProfileResponse?> get() = _userRespone

    private val _updatedImageUri = MutableStateFlow<String?>(null)
    val updatedImageUri: StateFlow<String?> get() = _updatedImageUri

    fun updateProfile(
        FirstName: String,
        LastName: String,
        Phone: String,
        Birthdate: String,
        Image: String,
        oneditProfileSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val response = apiServices.UpdateUser(
                Customer_inform(
                    firstName = FirstName,
                    lastName = LastName,
                    phone = Phone,
                    birthDate = Birthdate,
                    profileImage = Image
                )
            )
            if (response.isSuccessful) {
                _userRespone.value = response.body()
                _updatedImageUri.value = Image
                authInterceptor.setToken(response.body()?.token ?: "")
                oneditProfileSuccess()
            }
        }
    }
}
