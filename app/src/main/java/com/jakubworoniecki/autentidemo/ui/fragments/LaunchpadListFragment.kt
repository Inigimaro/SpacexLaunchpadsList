package com.jakubworoniecki.autentidemo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jakubworoniecki.autentidemo.R
import com.jakubworoniecki.autentidemo.databinding.LaunchpadListBinding
import com.jakubworoniecki.autentidemo.model.LaunchpadItem
import com.jakubworoniecki.autentidemo.model.LaunchpadViewModel
import com.jakubworoniecki.autentidemo.ui.adapters.LaunchpadsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchpadListFragment : Fragment(R.layout.launchpad_list),
    LaunchpadsAdapter.OnItemClickedInterface {
    private val TAG = "LaunchpadListFragment"
    private val viewModel by activityViewModels<LaunchpadViewModel>()
    private var _binding: LaunchpadListBinding? = null
    private val binding get() = _binding!!
    private lateinit var lAdapter: LaunchpadsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: fragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LaunchpadListBinding.bind(view)
        initUI()
        observe()
    }

    private fun initUI() {
        lAdapter = LaunchpadsAdapter(this)
        binding.launchpads.apply {
            setHasFixedSize(true)
            adapter = lAdapter
        }
    }

    private fun observe() {
        viewModel.launchpads.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.noLaunchpads.visibility = View.VISIBLE
            } else {
                binding.noLaunchpads.visibility = View.GONE
                lAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
            viewModel.changeIsLoading(false)
        }
    }

    override fun onItemClicked(item: LaunchpadItem) {
        val action =
            LaunchpadListFragmentDirections.actionLaunchpadListFragmentToLaunchpadDetails(item)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}