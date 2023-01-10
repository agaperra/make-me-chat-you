package com.agaperra.makemechatyou.ui.channel

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.agaperra.makemechatyou.R
import com.agaperra.makemechatyou.databinding.DialogChannelNameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 * Create channel dialog
 *
 * @constructor Create empty Create channel dialog
 */
@AndroidEntryPoint
class CreateChannelDialog : DialogFragment() {

    /**
     * Binding inflater
     *
     * Creating a binding instance
     */
    private var _binding: DialogChannelNameBinding? = null
    private val binding: DialogChannelNameBinding
        get() = _binding!!

    private val viewModel: ChannelViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    /**
     * On create dialog
     *
     * @param savedInstanceState
     * @return dialog
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogChannelNameBinding.inflate(layoutInflater)
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.choose_channel_name)
            .setView(binding.root)
            .setPositiveButton(R.string.create) { _, _ ->
                viewModel.createChannel(binding.etChannelName.text.toString())
            }
            .setNegativeButton(R.string.cancel){ dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }


    /**
     * On destroy
     *
     * make binding null
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}