package com.happymesport.merchant.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.happymesport.merchant.data.auth.FirebaseAuthDataSource
import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegate
import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegateImpl
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

    @Provides
    @Singleton
    fun provideProfileCompleteUseCases(tokenDelegate: AuthTokenPrefDelegate): ProfileCompleteUseCase =
        ProfileCompleteUseCase(
            readProfileCompleteUseCase = ReadProfileCompleteUseCase(tokenDelegate),
            saveProfileCompleteUseCase = SaveProfileCompleteUseCase(tokenDelegate),
        )

    @Provides
    @Singleton
    fun provideSaveProfileCompleteUseCase(tokenDelegate: AuthTokenPrefDelegate): SaveProfileCompleteUseCase =
        SaveProfileCompleteUseCase(tokenDelegate)

    @Provides
    @Singleton
    fun provideAppCheckHandelUseCase(
        repository: UserRepository,
        tokenDelegate: AuthTokenPrefDelegate,
    ): CheckAndHandleUserLoginUseCase = CheckAndHandleUserLoginUseCase(repository, SaveProfileCompleteUseCase(tokenDelegate))

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepository = UserRepositoryImpl(firestore)
}
