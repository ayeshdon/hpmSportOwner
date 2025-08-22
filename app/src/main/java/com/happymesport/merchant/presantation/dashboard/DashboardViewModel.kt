package com.happymesport.merchant.presantation.dashboard

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.data.dto.FacilityDto
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.domain.usecase.auth.ProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.auth.SaveAuthTokenUseCase
import com.happymesport.merchant.domain.usecase.auth.SaveProfileCompleteUseCase
import com.happymesport.merchant.domain.usecase.facility.GetFacilityByUserUseCase
import com.happymesport.merchant.domain.usecase.user.GetUserProfileDataUseCase
import com.happymesport.merchant.domain.usecase.user.SaveProfileDataUseCase
import com.happymesport.merchant.presantation.event.DashboardEvent
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
    @Inject
    constructor(
        private var profileCompleteUseCase: ProfileCompleteUseCase,
        private var getUserProfileDataUseCase: GetUserProfileDataUseCase,
        private var authTokenUseCase: SaveAuthTokenUseCase,
        private var saveProfileDataUseCase: SaveProfileDataUseCase,
        private var saveProfileCompleteUseCase: SaveProfileCompleteUseCase,
        private var getFacilityUC: GetFacilityByUserUseCase,
    ) : BaseViewModel<DashboardEvent>() {
        private val _profileCompleteState = MutableStateFlow(ViewState<Boolean>())
        val profileCompleteState = _profileCompleteState.asStateFlow()
        private val _profileDataState = MutableStateFlow(ViewState<UserModel>())
        val profileDataState = _profileDataState.asStateFlow()
        private val _facilityDataState = MutableStateFlow(ViewState<FacilityDto>())
        val facilityDataState = _facilityDataState.asStateFlow()

        override fun onEvent(event: DashboardEvent) {
            when (event) {
                is DashboardEvent.CheckProfileStatus -> {
                    profileCompleteUseCase()
                }

                DashboardEvent.GetProfileData -> {
                    getUserProfileData()
                }

                DashboardEvent.GetFacilityData -> {
                    getFacilityData()
                }
                else ->{}
            }
        }

        private fun getFacilityData() {
            viewModelScope.launch {
                Timber.e("VM_CHECK CALL 01")
                var uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                getFacilityUC.invoke(uid).collect {
                    when (it) {
                        is Resources.Error -> {
                            Timber.e("VM_CHECK ERRROR")
                            _facilityDataState.value =
                                ViewState(error = it.message, isLoading = false)

                            if (it.exception is FirebaseFirestoreException) {
                                val firebaseException = it.exception
                                if (firebaseException.code == FirebaseFirestoreException.Code.PERMISSION_DENIED) {
                                    Timber.e("PERMISSION_DENIED error detected. Triggering logout.")
                                    logout(
                                        authTokenUseCase,
                                        saveProfileCompleteUseCase,
                                        saveProfileDataUseCase,
                                    )
                                }
                            }
                        }

                        is Resources.Loading -> {
                            Timber.e("VM_CHECK LOADING")
                            _facilityDataState.value = ViewState(isLoading = true)
                        }

                        is Resources.Success -> {
                            Timber.e("VM_CHECK SUCCESS")
                            _facilityDataState.value =
                                ViewState(isLoading = false, data = it.data)
                        }
                    }
                }
            }
        }

        private fun getUserProfileData() {
            viewModelScope.launch {
                getUserProfileDataUseCase.invoke().collect {
                    _profileDataState.value = ViewState(data = it)
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
