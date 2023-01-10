package com.agaperra.makemechatyou.ui.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.agaperra.makemechatyou.R
import com.agaperra.makemechatyou.databinding.FragmentChannelBinding
import com.agaperra.makemechatyou.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory

/**
 * Channel fragment
 *
 * @constructor Create empty Channel fragment
 */
@AndroidEntryPoint
class ChannelFragment : BindingFragment<FragmentChannelBinding>() {

    /**
     * Binding inflater
     *
     * Creating a binding instance
     */
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentChannelBinding::inflate

    // the model will not be recreated
    private val viewModel: ChannelViewModel by activityViewModels()

    /**
     * On view created
     *
     * @param view root view parameter
     * @param savedInstanceState saved instance state parameter
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewModel.getUser()
        if (user == null) {
            findNavController().popBackStack()
            return
        }

        val factory = ChannelListViewModelFactory(
            filter = Filters.and(
                Filters.eq(
                    "type", "messaging"
                )
            ),
            sort = ChannelListViewModel.DEFAULT_SORT,
            limit = 30
        )

        // the models will be recreated every time
        val channelListViewModel: ChannelListViewModel by viewModels { factory }
        val channelListHeaderViewModel: ChannelListHeaderViewModel by viewModels()

        channelListViewModel.bindView(binding.channelListView, viewLifecycleOwner)
        channelListHeaderViewModel.bindView(binding.channelListHeaderView, viewLifecycleOwner)

        binding.channelListHeaderView.setOnUserAvatarClickListener {
            viewModel.logout()
            findNavController().popBackStack()
            Toast.makeText(
                requireContext(),
                getString(R.string.log_out),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}