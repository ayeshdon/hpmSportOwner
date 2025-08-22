package com.happymesport.merchant.di

import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegate
import com.happymesport.merchant.data.local.datastore.UserDataPrefDelegate
import com.happymesport.merchant.domain.repository.CommonSettingsRepository
import com.happymesport.merchant.domain.repository.FacilityRepository
import com.happymesport.merchant.domain.repository.UserRepository
import com.happymesport.merchant.domain.usecase.auth.AuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.ProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.auth.ReadAuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.ReadProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.auth.SaveAuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.SaveProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.common.CommonSettingsFacilityUseCase
import com.happymesport.merchant.domain.usecase.facility.GetFacilityByUserUseCase
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
object UseCasesModule {
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
    fun provideUserProfileSaveUseCases(pref: UserDataPrefDelegate): SaveProfileDataUseCase = SaveProfileDataUseCase(pref)

    @Provides
    @Singleton
    fun provideGetFacilityByUserUseCase(repo: FacilityRepository): GetFacilityByUserUseCase = GetFacilityByUserUseCase(repo)

    @Provides
    @Singleton
    fun provideGetCommonSettingsFacility(repo: CommonSettingsRepository): CommonSettingsFacilityUseCase =
        CommonSettingsFacilityUseCase(repo)

    @Provides
    @Singleton
    fun provideUserProfileReadUseCases(pref: UserDataPrefDelegate): GetUserProfileDataUseCase = GetUserProfileDataUseCase(pref)

    @Provides
    @Singleton
    fun provideSaveProfileCompleteUseCase(tokenDelegate: AuthTokenPrefDelegate): SaveProfileCompleteUseCase =
        SaveProfileCompleteUseCase(tokenDelegate)

    @Provides
    @Singleton
    fun provideSaveAuthTokenUseCase(tokenDelegate: AuthTokenPrefDelegate): SaveAuthTokenUseCase = SaveAuthTokenUseCase(tokenDelegate)

    @Provides
    @Singleton
    fun provideAppCheckHandelUseCase(
        repository: UserRepository,
        tokenDelegate: AuthTokenPrefDelegate,
        saveProfileDataUseCase: SaveProfileDataUseCase,
    ): CheckAndHandleUserLoginUseCase =
        CheckAndHandleUserLoginUseCase(
            repository,
            SaveProfileCompleteUseCase(tokenDelegate),
            saveProfileDataUseCase,
        )
}
