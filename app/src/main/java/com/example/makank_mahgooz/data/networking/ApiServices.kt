package com.example.makank_mahgooz.data.networking

import com.example.makank_mahgooz.data.model.BookingRequest
import com.example.makank_mahgooz.data.model.BookingResponse
import com.example.makank_mahgooz.data.model.Customer_inform
import com.example.makank_mahgooz.data.model.GetProfileResponse
import com.example.makank_mahgooz.data.model.LoginRequest
import com.example.makank_mahgooz.data.model.LoginResponse
import com.example.makank_mahgooz.data.model.RegisterRequest
import com.example.makank_mahgooz.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiServices {

    @POST("api/auth/signup")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
    @GET("api/users/me")
    suspend fun getProfile(): Response<GetProfileResponse>
    @PUT("api/users/me")
    suspend fun UpdateUser(@Body request: Customer_inform): Response<GetProfileResponse>
    @POST("api/bookings")
    suspend fun Booking(@Body request: BookingRequest): Response<BookingResponse>
    @GET("api/bookings")
    suspend fun getBooking(): Response<List<BookingResponse>>

}