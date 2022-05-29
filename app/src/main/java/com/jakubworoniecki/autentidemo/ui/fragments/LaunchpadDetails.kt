package com.jakubworoniecki.autentidemo.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jakubworoniecki.autentidemo.R
import com.jakubworoniecki.autentidemo.databinding.LaunchpadDetailsBinding

class LaunchpadDetails : Fragment(R.layout.launchpad_details) {
    private val args by navArgs<LaunchpadDetailsArgs>()
    private var _binding: LaunchpadDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LaunchpadDetailsBinding.bind(view)
        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        val item = args.launchpad
        val vehiclesList = item.vehicles_launched.toString().replace(",", "\n")
        binding.apply {
            status.text = item.status
            when (item.status) {
                "active" -> {
                    status.setTextColor(Color.GREEN)
                }
                "retired" -> {
                    status.setTextColor(Color.GRAY)
                }
                "under construction" -> {
                    status.setTextColor(Color.MAGENTA)
                }
            }
            name.text = item.site_name_long
            location.text = "${item.location.name}, ${item.location.region}"
            details.text = item.details
            vehicles.text = vehiclesList.subSequence(1, vehiclesList.length - 1)
            attemptedLaunches.text = item.attempted_launches.toString()
            successfulLaunches.text = item.successful_launches.toString()
            wikipedia.text = item.wikipedia
            latitude.text = item.location.latitude.toString()
            longitude.text = item.location.longitude.toString()
            showOnMap.setOnClickListener {
                val gmmUri = Uri.parse("geo:${item.location.latitude},${item.location.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }
}