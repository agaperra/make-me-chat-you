package com.agaperra.makemechatyou.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.agaperra.makemechatyou.databinding.FragmentLoginBinding
import com.agaperra.makemechatyou.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Login fragment
 *
 * @constructor Create empty Login fragment
 */
@AndroidEntryPoint
class LoginFragment:BindingFragment<FragmentLoginBinding>() {

    /**
     * Binding inflater
     *
     * Creating a binding instance
     */
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}