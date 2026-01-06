package com.example.makank_mahgooz.di

import com.example.makank_mahgooz.data.networking.ApiServices
import com.example.makank_mahgooz.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor() : AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideRetrofit(authInterceptor: AuthInterceptor) : Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs the request and response body
        }
        // Create an OkHttpClient and add the logging interceptor
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(0, java.util.concurrent.TimeUnit.MILLISECONDS)
            .readTimeout(0, java.util.concurrent.TimeUnit.MILLISECONDS)
            .writeTimeout(0, java.util.concurrent.TimeUnit.MILLISECONDS)
            .build()

        authInterceptor.setToken("")


        val retrofit = Retrofit.Builder()
            .baseUrl("https://makank-mahgooz.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

}