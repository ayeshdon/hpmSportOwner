package com.happymesport.merchant.presantation.facility

import androidx.lifecycle.viewModelScope
import com.happymesport.merchant.R
import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.common.UiText
import com.happymesport.merchant.data.dto.AvailableFacility
import com.happymesport.merchant.data.dto.FacilityDto
import com.happymesport.merchant.domain.usecase.common.CommonSettingsFacilityUseCase
import com.happymesport.merchant.domain.usecase.facility.AddFacilityUseCase
import com.happymesport.merchant.presantation.event.FacilityEvent
import com.happymesport.merchant.presantation.state.ViewState
import com.happymesport.merchant.presantation.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacilityCreateViewModel
    @Inject
    constructor(
        private var commonSettingsFacilityUseCase: CommonSettingsFacilityUseCase,
        private var saveFacilityUseCase: AddFacilityUseCase,
    ) : BaseViewModel<FacilityEvent>() {
        private val _facilityCommonDataState = MutableStateFlow(ViewState<List<AvailableFacility>>())
        val facilityCommonDataState = _facilityCommonDataState.asStateFlow()

        private val _saveFacilityState = MutableStateFlow(ViewState<String>())
        val saveFacilityState = _saveFacilityState.asStateFlow()

        private val _name = MutableStateFlow("")
        val name = _name.asStateFlow()

        private val _description = MutableStateFlow("")
        val description = _description.asStateFlow()

        private val _selectedList = MutableStateFlow<List<AvailableFacility>>(emptyList())
        val selectedList = _selectedList.asStateFlow()

        override fun onEvent(event: FacilityEvent) {
            when (event) {
                is FacilityEvent.GetFacilityList -> {
                    getFacilityList()
                }

                is FacilityEvent.AddFacilityList -> {
                    addFacilityList(event.facilityDto)
                }
            }
        }

        private fun addFacilityList(facilityDto: FacilityDto) {
            viewModelScope.launch {
                saveFacilityUseCase.invoke(facilityDto).collect { result ->
                    when (result) {
                        is Resources.Error -> {
                            _saveFacilityState.update {
                                it.copy(
                                    isLoading = false,
                                    data = null,
                                    error =
                                        "${UiText.StringResources(R.string.common_error)}\n${it.error}",
                                )
                            }
                        }

                        is Resources.Loading -> {
                            _saveFacilityState.update { it.copy(isLoading = true) }
                        }

                        is Resources.Success -> {
                            _saveFacilityState.update {
                                it.copy(
                                    isLoading = false,
                                    data = result.data,
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun getFacilityList() {
            viewModelScope.launch {
                commonSettingsFacilityUseCase.invoke().collect { result ->
                    when (result) {
                        is Resources.Error -> {
                            _facilityCommonDataState.update {
                                it.copy(
                                    isLoading = false,
                                    data = null,
                                    error =
                                        "${UiText.StringResources(R.string.common_error)}\n${it.error}",
                                )
                            }
                        }

                        is Resources.Loading -> {
                            _facilityCommonDataState.update { it.copy(isLoading = true) }
                        }

                        is Resources.Success -> {
                            _facilityCommonDataState.update {
                                it.copy(
                                    isLoading = false,
                                    data = result.data,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
