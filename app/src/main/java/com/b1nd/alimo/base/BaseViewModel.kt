package com.b1nd.alimo.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.utiles.Event
import com.b1nd.alimo.utiles.MutableEventFlow
import com.b1nd.alimo.utiles.asEventFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun viewEvent(content: String) =
        viewModelScope.launch {
            _eventFlow.emit(Event.Success(message = content))
        }

}