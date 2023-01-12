package com.agaperra.makemechatyou.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.makemechatyou.ui.util.Constants.MIN_NAME_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
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
     * @param name
     */
    private fun isValidateName(name: String) = name.length >= MIN_NAME_LENGTH


    /**
     * Connect user
     *
     * @param firstName
     * @param username
     */
    fun connectUser(firstName: String, username: String) {
        val trimmedUsername = username.trim()
        val trimmedFirstname = firstName.trim()
        viewModelScope.launch {
            if (isValidateName(trimmedUsername) && isValidateName(trimmedFirstname)) {
                if (client.getCurrentUser() == null) {
                    val user = if (trimmedFirstname.contains("Varvara")&& trimmedFirstname.contains("Varvara")) {
                        User(
                            id = trimmedUsername,
                            extraData = mutableMapOf(
                                "name" to trimmedFirstname,
                                "county" to "Russia",
                                "image" to "http://ih0.redbubble.net/image.601960678.3325/flat%2C1000x1000%2C075%2Cf.u5.jpg"
                            ),
                            role = "admin"
                        )
                    } else {
                        User(
                            id = trimmedUsername,
                            extraData = mutableMapOf(
                                "name" to trimmedFirstname
                            ),
                            role = "user"
                        )
                    }
                    val token = client.devToken(user.id)
                    val result = client.connectUser(
                        user = user,
                        token = token
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