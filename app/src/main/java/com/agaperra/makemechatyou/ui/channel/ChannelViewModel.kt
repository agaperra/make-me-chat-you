package com.agaperra.makemechatyou.ui.channel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Channel view model
 *
 * @property client
 * @constructor Create empty Channel view model
 */
@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val client: ChatClient
) : ViewModel() {

    private val _createChannelEvent = MutableSharedFlow<CreateChannelEvent>()
    val createChannelEvent = _createChannelEvent.asSharedFlow()

    fun createChannel(channelName: String) {
        val trimmedChannelName = channelName.trim()
        viewModelScope.launch {
            if (trimmedChannelName.isEmpty()) {
                _createChannelEvent.emit(CreateChannelEvent.Error("The channel name can't be empty."))
                return@launch
            }
            val result = client.channel(
                channelType = "messaging",
                channelId = "general"
            )
                .create(
                extraData = mapOf(
                    "name" to trimmedChannelName,
                ), memberIds = emptyList()
            ).await()
            if(result.isError){
                _createChannelEvent.emit(CreateChannelEvent.Error(result.error().message ?: "Unknown error"))
                return@launch
            }
            _createChannelEvent.emit(CreateChannelEvent.Success)
        }
    }

    /**
     * Log out function
     */
    fun logout() {
        client.disconnectSocket()
    }

    /**
     * Get user
     *
     * @return current user or null
     */
    fun getUser(): User? = client.getCurrentUser()

    sealed class CreateChannelEvent{
        data class Error(val error: String): CreateChannelEvent()
        object Success: CreateChannelEvent()
    }
}