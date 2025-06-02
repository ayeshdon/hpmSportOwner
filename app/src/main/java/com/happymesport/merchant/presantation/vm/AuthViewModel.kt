package com.happymesport.merchant.presantation.vm

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.happymesport.merchant.domain.usecase.auth.AuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.LoginWithMobileUseCase
import com.happymesport.merchant.domain.usecase.auth.OtpVerificationUseCase
import com.happymesport.merchant.presantation.event.AuthEvent
import com.happymesport.merchant.presantation.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        val authMobileUseCase: LoginWithMobileUseCase,
        val otpUseCase: OtpVerificationUseCase,
        private val authTokenUseCase: AuthTokenUseCase,
    ) : BaseViewModel<AuthEvent>() {
        private val _stateLoginMobile = MutableStateFlow(ViewState<String>())
        val stateLoginMobile = _stateLoginMobile.asStateFlow()
        private val _stateLoginOtp = MutableStateFlow(ViewState<String>())
        val stateLoginOtp = _stateLoginOtp.asStateFlow()
        private val _stateAuthFlag = MutableStateFlow(ViewState<Boolean>())
        val stateAuthFlag = _stateAuthFlag.asStateFlow()

        override fun onEvent(event: AuthEvent) {
            when (event) {
                is AuthEvent.LoginWithGoogle -> {}
                is AuthEvent.LoginWithFacebook -> {}
                is AuthEvent.LoginWithMobileReset -> {
                    _stateLoginMobile.value = ViewState(isLoading = false, data = null)
                }

                is AuthEvent.LoginWithMobile -> loginWithMobile(event.phone, event.activity)
                is AuthEvent.LoginWithMobileOtpVerify ->
                    otpVerification(
                        event.otp,
                        event.verificationId,
                    )

                is AuthEvent.GetAuthFlag -> {
                    viewModelScope.launch {
                        authTokenUseCase.readAuthToken.invoke().collect { isLoggedIn ->
                            _stateAuthFlag.value = ViewState(data = isLoggedIn)
                        }
                    }
                }

                is AuthEvent.Logout -> TODO()
            }
        }

        /**
         * Otp verification code
         */
        private fun otpVerification(
            otp: String,
            verificationId: String,
        ) {
            _stateLoginOtp.value = ViewState(isLoading = true)
            otpUseCase(verificationId, otp, onSuccess = {
                viewModelScope.launch {
                    authTokenUseCase.saveAuthToken(true)
                }

                _stateLoginOtp.value = ViewState(data = verificationId, isLoading = false)
            }, onError = { msg ->
                _stateLoginOtp.value = ViewState(error = msg, isLoading = false)
            }).launchIn(viewModelScope)
        }

        /**
         * **Login with mobile number**
         */
        private fun loginWithMobile(
            phoneNumber: String,
            activity: Activity,
        ) {
            _stateLoginMobile.value = ViewState(isLoading = true)
            authMobileUseCase(
                phoneNumber,
                onVerificationCompleted = { credential ->
                    credential.smsCode?.let { code ->
                        _stateLoginMobile.value = ViewState(isLoading = false)
                    }
                },
                onVerificationFailed = { exception ->
                    _stateLoginMobile.value = ViewState(error = exception.message, isLoading = false)
                },
                onCodeSent = { verificationId ->
                    _stateLoginMobile.value = ViewState(data = verificationId, isLoading = false)
                },
                activity,
            ).onEach {
            }.launchIn(viewModelScope)
        }
    }
