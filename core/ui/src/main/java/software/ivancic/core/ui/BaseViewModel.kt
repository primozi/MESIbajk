package software.ivancic.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * We should keep this class simple. Every viewmodel base functionality shouldn't be here.
 * This base class is only to make sure we all will follow the same pattern.
 */
abstract class BaseViewModel<ACTION, EFFECT, STATE>(defaultState: STATE) : ViewModel() {
    private val pendingActions = MutableSharedFlow<ACTION>()
    fun submitAction(action: ACTION) {
        viewModelScope.launch {
            pendingActions.emit(action)
        }
    }

    protected abstract suspend fun handleAction(action: ACTION)

    private val _effects = MutableSharedFlow<EFFECT>()
    val effects: SharedFlow<EFFECT> = _effects.asSharedFlow()
    protected suspend fun emitEffect(effect: EFFECT) {
        _effects.emit(effect)
    }

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(defaultState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    protected fun updateState(function: (oldState: STATE) -> STATE) {
        _state.update { function(it) }
    }

    init {
        viewModelScope.launch {
            pendingActions.collect { action ->
                handleAction(action)
            }
        }
    }
}
