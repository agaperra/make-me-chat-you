package com.agaperra.makemechatyou.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import javax.inject.Inject

/**
 * Login view model
 *
 * @property client parameter for the chat client
 * @constructor Create empty Login view model
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client: ChatClient
) : ViewModel() {
}