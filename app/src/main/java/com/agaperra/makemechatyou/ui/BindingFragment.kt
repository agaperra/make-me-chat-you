package com.agaperra.makemechatyou.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Binding fragment: base fragment with view binding
 *
 * @param T can be any viewBinding
 * @constructor Create empty Binding fragment
 */

abstract class BindingFragment<out T: ViewBinding>: Fragment(){

    /**
     * _binding
     *
     * Create a view binding container
     */
    private var _binding: ViewBinding? = null

    /**
     * Binding
     *
     * Filling view binding
     */
    @Suppress("UNCHECKED_CAST")
    protected val binding: T
        get() = _binding as T

    /**
     * On create view
     *
     * @param inflater is a layout inflater
     * @param container parameter, which is a view group
     * @param savedInstanceState saved instance state parameter
     * @return view in the associated layout file
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater)
        return  _binding!!.root
    }

    /**
     * On destroy view
     *
     * The method that releases binding
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Binding inflater
     *
     * Method that converts layout inflater to view binding
     */
    protected abstract val bindingInflater: (LayoutInflater) -> ViewBinding
}