package com.happymesport.merchant.di

import com.google.firebase.auth.FirebaseAuth
import com.happymesport.merchant.data.auth.AuthRepositoryImpl
import com.happymesport.merchant.data.auth.FirebaseAuthDataSource
import com.happymesport.merchant.domain.auth.AuthRepository
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
}
