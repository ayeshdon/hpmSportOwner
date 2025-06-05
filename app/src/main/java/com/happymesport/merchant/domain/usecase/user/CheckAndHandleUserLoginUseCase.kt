package com.happymesport.merchant.domain.usecase.user

import com.google.firebase.Timestamp
import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.domain.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class CheckAndHandleUserLoginUseCase
    @Inject
    constructor(
        private val authRepository: UserRepository,
    ) {
        suspend operator fun invoke(
            uid: String,
            mobileNumber: String,
        ): Resources<UserModel> {
            val currentTime = Timestamp.now()

            val result = authRepository.getUser(uid)

            return when (result) {
                is Resources.Success -> {
                    val existingUser = result.data
                    if (existingUser == null) {
                        Timber.e("NEW USER")
                        val newUser =
                            UserModel(
                                uuid = uid,
                                mobileNumber = mobileNumber,
                                createdAt = currentTime.toDate().toString(),
                                lastLoginTime = currentTime.toDate().toString(),
                            )
                        val addResult = authRepository.addUser(newUser)
                        when (addResult) {
                            is Resources.Success -> Resources.Success(newUser)
                            is Resources.Error ->
                                Resources.Error(
                                    addResult.message ?: "Unknown error adding user",
                                )

                            is Resources.Loading -> Resources.Loading()
                        }
                    } else {
                        val updateResult = authRepository.updateLastLoginTime(uid, currentTime)
                        when (updateResult) {
                            is Resources.Success ->
                                Resources.Success(
                                    existingUser.copy(
                                        lastLoginTime = currentTime.toDate().toString(),
                                    ),
                                )

                            is Resources.Error ->
                                Resources.Error(
                                    updateResult.message ?: "Unknown error updating login time",
                                )

                            is Resources.Loading -> Resources.Loading()
                        }
                    }
                }

                is Resources.Error ->
                    Resources.Error(
                        result.message ?: "Unknown error checking user existence",
                    )

                is Resources.Loading -> Resources.Loading()
            }
        }
    }
