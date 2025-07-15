package com.happymesport.merchant.presantation.dashboard

import androidx.lifecycle.viewModelScope
import com.happymesport.merchant.domain.usecase.auth.ProfileCompleteUseCase
import com.happymesport.merchant.presantation.event.DashboardEvent
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
    @Inject
    constructor(
        var profileCompleteUseCase: ProfileCompleteUseCase,
    ) : BaseViewModel<DashboardEvent>() {
        private val _profileCompleteState = MutableStateFlow(ViewState<Boolean>())
        val profileCompleteState = _profileCompleteState.asStateFlow()

        override fun onEvent(event: DashboardEvent) {
            when (event) {
                is DashboardEvent.CheckProfileStatus -> {
                    profileCompleteUseCase()
                }
            }
        }

        private fun profileCompleteUseCase() {
            viewModelScope.launch {
                profileCompleteUseCase.readProfileCompleteUseCase.invoke().collect {
                    _profileCompleteState.value = ViewState(data = it)
                }
            }
        }
    }
