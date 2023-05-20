package com.example.currencyconvertor.di

import com.example.currencyconvertor.domain.repo.RemoteRepo
import com.example.currencyconvertor.domain.usecase.MainUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun mainUseCase(remoteRepo: RemoteRepo) : MainUseCase
    {
        return MainUseCase(remoteRepo)
    }

}