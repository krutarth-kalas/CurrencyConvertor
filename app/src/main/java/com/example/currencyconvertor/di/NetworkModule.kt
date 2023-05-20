package com.example.currencyconvertor.di

import android.app.Application
import com.example.currencyconvertor.R
import com.example.currencyconvertor.data.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideService(context : Application,client: OkHttpClient) : Network {
        val retrofit =   Retrofit.Builder().
                baseUrl("https://api.apilayer.com/")
                .client(client)
            .addConverterFactory(GsonConverterFactory.create())
                .build()
       return retrofit.create(Network::class.java)
    }

    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("apikey","6oNvKfk2jm7dkwRwEwJ7OIl7jZFmdiK9")
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    fun provideHttpClient(interceptor: Interceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor())
            .build()

    }
}