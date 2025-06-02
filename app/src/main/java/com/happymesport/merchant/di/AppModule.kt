package com.happymesport.merchant.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.happymesport.merchant.data.auth.AuthRepositoryImpl
import com.happymesport.merchant.data.auth.FirebaseAuthDataSource
import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegate
import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegateImpl
import com.happymesport.merchant.domain.repository.AuthRepository
import com.happymesport.merchant.domain.usecase.auth.AuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.ReadAuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.SaveAuthTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: FirebaseAuthDataSource): AuthRepository = AuthRepositoryImpl(authDataSource)

    @Provides
    @Singleton
    fun provideAuthCodePrefDelegate(application: Application): AuthTokenPrefDelegate = AuthTokenPrefDelegateImpl(context = application)

    @Provides
    @Singleton
    fun provideAppAuthCodeUseCases(tokenDelegate: AuthTokenPrefDelegate): AuthTokenUseCase =
        AuthTokenUseCase(
            readAuthToken = ReadAuthTokenUseCase(tokenDelegate),
            saveAuthToken = SaveAuthTokenUseCase(tokenDelegate),
        )
}
