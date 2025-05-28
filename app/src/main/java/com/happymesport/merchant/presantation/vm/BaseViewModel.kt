package com.happymesport.merchant.presantation.vm

import androidx.lifecycle.ViewModel
import com.happymesport.merchant.presantation.event.BaseEvent

abstract class BaseViewModel<E : BaseEvent> : ViewModel() {
    abstract fun onEvent(event: E)
}
