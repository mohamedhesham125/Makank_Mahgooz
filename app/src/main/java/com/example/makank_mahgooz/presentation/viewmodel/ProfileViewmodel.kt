package com.example.makank_mahgooz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makank_mahgooz.data.model.GetProfileResponse
import com.example.makank_mahgooz.data.model.RegisterResponse
import com.example.makank_mahgooz.data.networking.ApiServices
import com.example.makank_mahgooz.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(
    private val apiServices: ApiServices,
    private val authInterceptor: AuthInterceptor
) : ViewModel() {

    private val _userRespone = MutableStateFlow<GetProfileResponse?>(null)
    val userRespone: StateFlow<GetProfileResponse?> get() = _userRespone

    // ✅ صورة البروفايل المحدثة
    private val _updatedImageUri = MutableStateFlow<Any?>(null)
    val updatedImageUri: StateFlow<Any?> get() = _updatedImageUri

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            val response = apiServices.getProfile()
            if (response.isSuccessful) {
                _userRespone.value = response.body()
            }
        }
    }

    // ✅ استدعِ هذه الدالة من EditProfileScreen بعد اختيار صورة جديدة
    fun setUpdatedImage(uri: Any) {
        _updatedImageUri.value = uri
    }
}
