package com.happymesport.merchant.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.happymesport.merchant.data.auth.FirebaseAuthDataSource
import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegate
import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegateImpl
import com.happymesport.merchant.data.local.datastore.UserDataPrefDelegate
import com.happymesport.merchant.data.local.datastore.UserDataPrefDelegateImpl
import com.happymesport.merchant.data.repository.AuthRepositoryImpl
import com.happymesport.merchant.data.repository.UserRepositoryImpl
import com.happymesport.merchant.domain.repository.AuthRepository
import com.happymesport.merchant.domain.repository.UserRepository
import com.happymesport.merchant.domain.usecase.auth.AuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.ProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.auth.ReadAuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.ReadProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.auth.SaveAuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.SaveProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.user.CheckAndHandleUserLoginUseCase
import com.happymesport.merchant.domain.usecase.user.GetUserProfileDataUseCase
import com.happymesport.merchant.domain.usecase.user.SaveProfileDataUseCase
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
    fun provideAuthCodePrefDelegate(application: Application): AuthTokenPrefDelegate = AuthTokenPrefDelegateImpl(context = application)

    @Provides
    @Singleton
    fun provideUserProfilePrefDelegate(application: Application): UserDataPrefDelegate = UserDataPrefDelegateImpl(context = application)


    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}
