package com.example.currencyconvertor.di

import com.example.currencyconvertor.data.network.Network
import com.example.currencyconvertor.data.repo.RepoImpl
import com.example.currencyconvertor.domain.repo.RemoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun repoModule(network : Network) : RemoteRepo{
        return RepoImpl(network)
    }

}