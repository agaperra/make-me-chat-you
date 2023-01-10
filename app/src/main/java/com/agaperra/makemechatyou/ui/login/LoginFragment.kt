package com.agaperra.makemechatyou.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.agaperra.makemechatyou.R
import com.agaperra.makemechatyou.databinding.FragmentLoginBinding
import com.agaperra.makemechatyou.ui.BindingFragment
import com.agaperra.makemechatyou.ui.util.Constants
import dagger.hilt.android.AndroidEntryPoint

/**
 * Login fragment
 *
 * @constructor Create empty Login fragment
 */
@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>() {

    /**
     * Binding inflater
     *
     * Creating a binding instance
     */
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    private val viewModel: LoginViewModel by viewModels()

    /**
     * On view created
     *
     * @param view root view parameter
     * @param savedInstanceState saved instance state parameter
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            setupConnectingUiState()
            viewModel.connectUser(binding.etUsername.text.toString())
        }

        with(binding.etUsername) {
            addTextChangedListener {
                error = null
            }
        }
        subscribeToEvents()
    }

    /**
     * Subscribe to events
     *
     * This function performs various actions depending on the event that occurred
     *
     */
    private fun subscribeToEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect { event ->
                when (event) {
                    is LoginViewModel.LogInEvent.ErrorInputTooShort -> {
                        setupIdleUiState()
                        binding.etUsername.error = getString(
                            R.string.error_username_too_short,
                            Constants.MIN_USERNAME_LENGTH
                        )
                    }
                    is LoginViewModel.LogInEvent.ErrorLogIn -> {
                        setupIdleUiState()
                        Toast.makeText(
                            requireContext(),
                            event.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoginViewModel.LogInEvent.Success -> {
                        setupIdleUiState()
                        Toast.makeText(
                            requireContext(),
                            "successful log in",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    /**
     * Setup connecting ui state
     *
     * changing view settings
     */
    private fun setupConnectingUiState() {
        binding.progressBar.isVisible = true
        binding.btnConfirm.isEnabled = false
    }

    /**
     * Setup idle ui state
     *
     * changing view settings
     */
    private fun setupIdleUiState() {
        binding.progressBar.isVisible = false
        binding.btnConfirm.isEnabled = true
    }
}