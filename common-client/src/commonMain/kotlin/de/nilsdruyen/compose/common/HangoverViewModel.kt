package de.nilsdruyen.compose.common

import de.nilsdruyen.compose.common.data.HangoverRepository
import de.nilsdruyen.compose.common.entities.ThemeEntity
import de.nilsdruyen.compose.common.model.HangoverState
import de.nilsdruyen.compose.common.model.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HangoverViewModel(
    private val scope: CoroutineScope,
    private val hangoverRepository: HangoverRepository,
) {

    private val _state: MutableStateFlow<HangoverState> = MutableStateFlow(HangoverState())
    val state: StateFlow<HangoverState> = _state.asStateFlow()

    private var themeJob: Job? = null

    private val actionChannel = Channel<String>(Channel.BUFFERED)
//    private val queue: LinkedList<String> = LinkedList()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun observe() {
        _state.value = state.value.copy(inSync = true)
        themeJob?.cancel()
        themeJob = scope.launch {
            try {
                hangoverRepository.observeStyle().collect {
                    _state.value = state.value.copy(theme = it.map())
                }
                _state.value = state.value.copy(inSync = false)
            } catch (e: Exception) {
                println("error websocket ${e.message}")
                _state.value = state.value.copy(inSync = false)
            }
        }

//        scope.launch {
//            actionChannel.consumeAsFlow()
//                .scan(mutableSetOf()) { actions: MutableSet<String>, new: String ->
//                    actions += new
//                    // TODO: merge existing actions with the same productId
//                    actions
//                }
//                .flatMapLatest {
//                    flow { it.forEach { emit(it) } }
//                }
//                .debounce(1500)
//                .distinctUntilChanged()
//                .collect {
//                    val currentEvents = state.value.consumed
//                    _state.value = state.value.copy(consumed = currentEvents + "event${currentEvents.size}")
//                    println("pending action $it")
//                }
//        }
    }

    fun disconnect() {
        scope.launch {
            themeJob?.cancelAndJoin()
            themeJob = null
            _state.value = state.value.copy(inSync = false)
        }
    }

    fun add() {
        val currentEvents = state.value.events
        val event = "event${currentEvents.size}"
        _state.value = state.value.copy(events = currentEvents + event)
        scope.launch { actionChannel.send(event) }
    }

    fun startServer() {
        _state.value = state.value.copy(serverIsRunning = true)
    }

    fun stopServer() {
        _state.value = state.value.copy(serverIsRunning = true)
    }
}

fun ThemeEntity.map(): Theme {
    return Theme(
        colors = colors,
    )
}