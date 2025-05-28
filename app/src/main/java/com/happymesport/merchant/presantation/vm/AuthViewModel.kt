package com.happymesport.merchant.presantation.vm

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.happymesport.merchant.domain.auth.LoggedUser
import com.happymesport.merchant.domain.auth.LoginWithFacebookUseCase
import com.happymesport.merchant.domain.auth.LoginWithGoogleUseCase
import com.happymesport.merchant.domain.auth.LoginWithMobileUseCase
import com.happymesport.merchant.presantation.event.AuthEvent
import com.happymesport.merchant.presantation.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        val authUseCase: LoginWithGoogleUseCase,
        val authFbUseCase: LoginWithFacebookUseCase,
        val authMobileUseCase: LoginWithMobileUseCase,
    ) : BaseViewModel<AuthEvent>() {
        private val _state = MutableStateFlow(ViewState<LoggedUser>())
        val state = _state.asStateFlow()
        private val _stateLoginMobile = MutableStateFlow(ViewState<String>())
        val stateLoginMobile = _stateLoginMobile.asStateFlow()

        override fun onEvent(event: AuthEvent) {
            when (event) {
                is AuthEvent.LoginWithGoogle -> {}
                is AuthEvent.LoginWithFacebook -> {}
                is AuthEvent.LoginWithMobileReset -> {
                    _stateLoginMobile.value = ViewState(isLoading = false, data = null)
                }

                is AuthEvent.LoginWithMobile -> loginWithMobile(event.phone, event.activity)
                AuthEvent.Logout -> TODO()
            }
        }

//        private fun loginWithGoogle(idToken: String) {
//            authUseCase(idToken)
//                .onEach {
//                    when (it) {
//                        is Resources.Error -> _state.value = ViewState(error = it.message)
//                        is Resources.Loading -> _state.value = ViewState(isLoading = true)
//                        is Resources.Success -> _state.value = ViewState(data = it.data)
//                    }
//                }.launchIn(viewModelScope)
//        }

//        private fun loginWithFacebook(idToken: String) {
//            authFbUseCase(idToken)
//                .onEach {
//                    when (it) {
//                        is Resources.Error -> _state.value = ViewState(error = it.message)
//                        is Resources.Loading -> _state.value = ViewState(isLoading = true)
//                        is Resources.Success -> _state.value = ViewState(data = it.data)
//                    }
//                }.launchIn(viewModelScope)
//        }

        private fun loginWithMobile(
            phoneNumber: String,
            activity: Activity,
        ) {
            _stateLoginMobile.value = ViewState(isLoading = true)
            authMobileUseCase(
                phoneNumber,
                onVerificationCompleted = { credential ->
                    credential.smsCode?.let { code ->
                        Timber.e("CALL 02")
                        _stateLoginMobile.value = ViewState(isLoading = false)
                        Timber.e("LOGIN COMPLETED CODE : $code")
//                        onCodeChanged(code)
//                        verifyOtp()
                    }
                },
                onVerificationFailed = { exception ->
                    Timber.e("CALL 03")
                    _stateLoginMobile.value = ViewState(error = exception.message, isLoading = false)
                },
                onCodeSent = { verificationId ->
                    Timber.e("CALL 04")
                    _stateLoginMobile.value = ViewState(data = verificationId, isLoading = false)
                },
                activity,
            ).onEach {
            }.launchIn(viewModelScope)
        }
    }
