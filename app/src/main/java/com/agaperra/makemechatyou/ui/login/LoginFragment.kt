package com.agaperra.makemechatyou.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.agaperra.makemechatyou.R
import com.agaperra.makemechatyou.databinding.FragmentLoginBinding
import com.agaperra.makemechatyou.ui.BindingFragment
import com.agaperra.makemechatyou.ui.util.Constants
import com.agaperra.makemechatyou.ui.util.navigateSafely
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

    // the model will be recreated every time
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var sPrefs: SharedPreferences

    /**
     * On view created
     *
     * @param view root view parameter
     * @param savedInstanceState saved instance state parameter
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)

        if(sPrefs.getString("firstname", null) != null && sPrefs.getString("username", null) != null){
            viewModel.connectUser(
                sPrefs.getString("firstname", "") ?: "",
                sPrefs.getString("username", "") ?: ""
            )
        }
        binding.btnConfirm.setOnClickListener {
            setupConnectingUiState()
            if(sPrefs.getString("firstname", null) == null && sPrefs.getString("username", null) == null){
                sPrefs.edit().putString("firstname", binding.etFirstName.text.toString()).apply()
                sPrefs.edit().putString("username", binding.etUsername.text.toString()).apply()
            }
            viewModel.connectUser(
                sPrefs.getString("firstname", binding.etFirstName.text.toString()) ?: binding.etFirstName.text.toString(),
                sPrefs.getString("username", binding.etUsername.text.toString()) ?: binding.etUsername.text.toString()
            )

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
                            Constants.MIN_NAME_LENGTH
                        )
                    }
                    is LoginViewModel.LogInEvent.ErrorLogIn -> {
                        setupIdleUiState()
                        Toast.makeText(
                            requireContext(),
                            event.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is LoginViewModel.LogInEvent.Success -> {
                        setupIdleUiState()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.success_log_in),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigateSafely(
                            R.id.action_loginFragment_to_channelFragment
                        )
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