package com.agaperra.makemechatyou.ui.channel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
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

}