package com.happymesport.merchant.di

import com.google.firebase.firestore.FirebaseFirestore
import com.happymesport.merchant.data.auth.FirebaseAuthDataSource
import com.happymesport.merchant.data.repository.AuthRepositoryImpl
import com.happymesport.merchant.data.repository.CommonSettingsRepositoryImpl
import com.happymesport.merchant.data.repository.FacilityRepositoryImpl
import com.happymesport.merchant.data.repository.UserRepositoryImpl
import com.happymesport.merchant.domain.repository.AuthRepository
import com.happymesport.merchant.domain.repository.CommonSettingsRepository
import com.happymesport.merchant.domain.repository.FacilityRepository
import com.happymesport.merchant.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepository = UserRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideFacilityRepository(firestore: FirebaseFirestore): FacilityRepository = FacilityRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: FirebaseAuthDataSource): AuthRepository = AuthRepositoryImpl(authDataSource)

    @Provides
    @Singleton
    fun provideCommonSettingsRepository(firestore: FirebaseFirestore): CommonSettingsRepository = CommonSettingsRepositoryImpl(firestore)
}
