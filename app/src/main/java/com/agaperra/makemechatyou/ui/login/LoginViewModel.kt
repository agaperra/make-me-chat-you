package com.agaperra.makemechatyou.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.makemechatyou.ui.util.Constants.MIN_USERNAME_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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

    // access from the fragment is closed
    private val _loginEvent = MutableSharedFlow<LogInEvent>()

    // access from the fragment is open
    val loginEvent = _loginEvent.asSharedFlow()


    /**
     * Is validate user name
     *
     * This method checks the user name for correctness in length
     *
     * @param username
     */
    private fun isValidateUserName(username: String) = username.length >= MIN_USERNAME_LENGTH


    fun connectUser(username: String) {
        val trimmedUsername = username.trim()
        viewModelScope.launch {
            if (isValidateUserName(trimmedUsername)) {
                val result = client.connectGuestUser(
                    userId = trimmedUsername,
                    username = trimmedUsername
                ).await()
                if (result.isError) {
                    _loginEvent.emit(
                        LogInEvent.ErrorLogIn(
                            result.error().message ?: "Unknown error"
                        )
                    )
                    return@launch
                }
                _loginEvent.emit(LogInEvent.Success)
            } else {
                _loginEvent.emit(LogInEvent.ErrorInputTooShort)
            }
        }
    }

    /**
     * LogIn event
     *
     * Sealed class for error handling or correct operation
     *
     * @constructor Create empty LogIn event
     */
    sealed class LogInEvent {
        // error from the client side
        object ErrorInputTooShort : LogInEvent()

        // error from the server side
        data class ErrorLogIn(val error: String) : LogInEvent()

        // success log in
        object Success : LogInEvent()
    }


}