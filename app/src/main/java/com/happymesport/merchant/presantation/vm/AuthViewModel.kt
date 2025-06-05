package com.happymesport.merchant.presantation.vm

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.happymesport.merchant.domain.usecase.auth.AuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.LoginWithMobileUseCase
import com.happymesport.merchant.domain.usecase.auth.OtpVerificationUseCase
import com.happymesport.merchant.domain.usecase.user.CheckAndHandleUserLoginUseCase
import com.happymesport.merchant.presantation.event.AuthEvent
import com.happymesport.merchant.presantation.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        val authMobileUseCase: LoginWithMobileUseCase,
        val otpUseCase: OtpVerificationUseCase,
        val handleUserLoginUseCase: CheckAndHandleUserLoginUseCase,
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
                        mobileNumber = event.phone,
                        otp = event.otp,
                        verificationId = event.verificationId,
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
            mobileNumber: String,
            verificationId: String,
        ) {
            Timber.e("OTP MOBILE : $mobileNumber")
            Timber.e("OTP CODE : $otp")
            Timber.e("OTP VERIFICATION ID : $verificationId")

            _stateLoginOtp.value = ViewState(isLoading = true)
            otpUseCase(verificationId = verificationId, code = otp, onSuccess = {
                viewModelScope.launch {
                    authTokenUseCase.saveAuthToken(true)
                    FirebaseAuth.getInstance().currentUser?.let {
                        var uid = it.uid
                        Timber.e("LOGGED UID : $uid")
                        Timber.e("LOGGED Name  : ${it.displayName}")
                        Timber.e("LOGGED email  : ${it.email}")
                        handleUserLoginUseCase(uid, mobileNumber)
                    }
                }

                _stateLoginOtp.value = ViewState(data = verificationId, isLoading = false)
            }, onError = { msg ->
                Timber.e("OTP ERROR : $msg")
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
