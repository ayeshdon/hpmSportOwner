package com.happymesport.merchant.presantation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.happymesport.merchant.domain.usecase.auth.SaveAuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.SaveProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.user.SaveProfileDataUseCase
import com.happymesport.merchant.presantation.event.BaseEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<E : BaseEvent> : ViewModel() {
    abstract fun onEvent(event: E)

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent: SharedFlow<Unit> = _logoutEvent.asSharedFlow()

    fun logout(
        saveAuthTokenUseCase: SaveAuthTokenUseCase,
        profileUseCase: SaveProfileCompleteUseCase,
        saveUserProfileUseCase: SaveProfileDataUseCase,
    ) {
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance().signOut()
                Timber.d("User successfully signed out from Firebase.")

                saveAuthTokenUseCase.invoke(false)
                profileUseCase.invoke(false)
                saveUserProfileUseCase.invoke(null)
                _logoutEvent.emit(Unit)
            } catch (e: Exception) {
                Timber.e("Error during Firebase sign out: ${e.message}")
            }
        }
    }
}
