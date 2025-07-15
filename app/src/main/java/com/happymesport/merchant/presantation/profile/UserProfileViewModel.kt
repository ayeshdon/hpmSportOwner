package com.happymesport.merchant.presantation.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.happymesport.merchant.R
import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.common.UiText
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.domain.repository.UserRepository
import com.happymesport.merchant.domain.usecase.auth.SaveProfileCompleteUseCase
import com.happymesport.merchant.presantation.event.UserProfileEvent
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.vm.BaseViewModel
import com.happymesport.merchant.utils.FirestoreCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val saveProfileCompleteUseCase: SaveProfileCompleteUseCase,
    ) : BaseViewModel<UserProfileEvent>() {
        private val _profileImgPickerState = MutableStateFlow(ViewState<ProfileImageUploadIEffect>())
        val profileImgPickerState = _profileImgPickerState.asStateFlow()
        private val _profileImgUploadState = MutableStateFlow(ViewState<String>())
        val profileImgUploadState = _profileImgUploadState.asStateFlow()
        private val _profileDetailsState = MutableStateFlow(ViewState<UserModel?>())
        val profileDetailsState = _profileDetailsState.asStateFlow()
        private val _profileUpdateState = MutableStateFlow(ViewState<String>())
        val profileUpdateState = _profileUpdateState.asStateFlow()

        private val storageRef = Firebase.storage.reference

        override fun onEvent(event: UserProfileEvent) {
            when (event) {
                is UserProfileEvent.GetProfileData -> getProfileDetails()
                is UserProfileEvent.ImageSelected -> uploadImage(event.uri)
                UserProfileEvent.PickImage -> {
                    viewModelScope.launch {
                        _profileImgPickerState.value =
                            ViewState(data = ProfileImageUploadIEffect.ShowAlertImagePicker)
                    }
                }

                is UserProfileEvent.UpdateProfile -> {
                    updateProfileDataForInitLogin(event.name, event.email, event.dob)
                }
            }
        }

        /**
         * update user profile after init login
         */
        private fun updateProfileDataForInitLogin(
            name: String,
            email: String,
            dob: String,
        ) {
            FirebaseAuth.getInstance().currentUser?.uid?.let {
                _profileUpdateState.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    var result = userRepository.updateProfileProfile(name, email, dob, it)
                    when (result) {
                        is Resources.Success -> {
                            saveProfileCompleteUseCase.invoke(true)

                            _profileUpdateState.update {
                                it.copy(
                                    isLoading = false,
                                    data = result.data.toString(),
                                )
                            }
                        }

                        is Resources.Error ->
                            _profileUpdateState.update {
                                it.copy(
                                    isLoading = false,
                                    data = null,
                                    error =
                                        result.message
                                            ?: "${UiText.StringResources(R.string.error_update_user_profile)}",
                                )
                            }

                        is Resources.Loading -> _profileUpdateState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }

        /**
         * get profile details
         */
        private fun getProfileDetails() {
            var uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            _profileDetailsState.update { it.copy(isLoading = true) }
            try {
                viewModelScope.launch {
                    var model = userRepository.getUser(uid)
                    _profileDetailsState.update { it.copy(isLoading = false, data = model.data) }
                }
            } catch (e: Exception) {
                _profileDetailsState.update { it.copy(isLoading = false, error = "${e.message}") }
            }
        }

        /**
         * upload profile image url
         */
        private fun uploadImage(uri: Uri) {
            var uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val imageRef = storageRef.child("${FirestoreCollections.PROFILEIMAGEDIR}/$uid.png")
            val uploadTask = imageRef.putFile(uri)
            uploadTask
                .addOnProgressListener { taskSnapshot ->
                    _profileImgUploadState.value = ViewState(isLoading = true)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUrl ->
                        Timber.e("Download URL: $downloadUrl")
                        viewModelScope.launch {
                            userRepository.updateProfileImage(downloadUrl.toString(), uid)
                            _profileImgUploadState.update {
                                it.copy(
                                    isLoading = false,
                                    data = "$downloadUrl",
                                    error = null,
                                )
                            }
                        }
                    }
                }.addOnFailureListener { exception ->
                    _profileImgUploadState.update {
                        it.copy(
                            isLoading = false,
                            data = null,
                            error = "Upload failed: ${exception.message}",
                        )
                    }
                }
        }
    }
