package com.jakubworoniecki.autentidemo.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakubworoniecki.autentidemo.databinding.LaunchpadItemBinding
import com.jakubworoniecki.autentidemo.model.LaunchpadItem

class LaunchpadsAdapter(private val listener: OnItemClickedInterface) :
    PagingDataAdapter<LaunchpadItem, LaunchpadsAdapter.LaunchpadsViewHolder>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<LaunchpadItem>() {
            override fun areItemsTheSame(oldItem: LaunchpadItem, newItem: LaunchpadItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LaunchpadItem,
                newItem: LaunchpadItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchpadsViewHolder {
        val binding = LaunchpadItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LaunchpadsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchpadsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindTo(item)
        } else holder.clear()
    }

    inner class LaunchpadsViewHolder(private val binding: LaunchpadItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClicked(item)
                    }
                }
            }
        }
        @SuppressLint("SetTextI18n")
        fun bindTo(item: LaunchpadItem) {
            binding.apply {
                name.text = item.site_name_long
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
                location.text = "${item.location.name}, ${item.location.region}"
                val vehiclesString = item.vehicles_launched.toString()
                vehiclesLaunched.text = vehiclesString.subSequence(1, vehiclesString.length -1)
                attemptedLaunches.text = "Attempted: ${item.attempted_launches}"
                successfulLaunches.text = "Successful: ${item.successful_launches}"
            }
        }

        fun clear() {
            binding.apply {
                name.text = ""
                status.text = ""
                location.text = ""
                vehiclesLaunched.text = ""
                attemptedLaunches.text = ""
                successfulLaunches.text = ""
            }
        }
    }

    interface OnItemClickedInterface {
        fun onItemClicked(item: LaunchpadItem)
    }
}
