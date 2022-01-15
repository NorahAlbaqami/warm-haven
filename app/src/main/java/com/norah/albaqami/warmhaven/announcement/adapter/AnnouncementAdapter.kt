package com.norah.albaqami.warmhaven.announcement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norah.albaqami.warmhaven.announcement.ui.AnnouncementListFragment
import com.norah.albaqami.warmhaven.announcement.ui.AnnouncementListFragmentDirections
import com.norah.albaqami.warmhaven.databinding.AnnouncementCardBinding

import com.norah.albaqami.warmhaven.network.AnnouncementItem
import com.norah.albaqami.warmhaven.pet.ui.PetsListFragmentDirections


class AnnouncementAdapter : ListAdapter<AnnouncementItem, AnnouncementAdapter.AnnouncementViewHolder>(
    DiffCallback
)  {
    class AnnouncementViewHolder( var binding: AnnouncementCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(views: AnnouncementItem) {
            binding.result= views
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }
    companion object DiffCallback : DiffUtil.ItemCallback<AnnouncementItem>() {
        override fun areItemsTheSame(oldItem: AnnouncementItem, newItem: AnnouncementItem): Boolean {
            return oldItem.id == newItem.id
        }
 override fun areContentsTheSame(
            oldItem: AnnouncementItem,
            newItem: AnnouncementItem
        ): Boolean {
     return oldItem.image == newItem.image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
      return AnnouncementViewHolder(
        AnnouncementCardBinding.inflate(LayoutInflater.from(parent.context))
      )
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        val petPhoto = getItem(position)
        holder.bind(petPhoto)
         petPhoto.id.toString()
        holder.binding.AnnouncementCard.setOnClickListener {
            val action = AnnouncementListFragmentDirections.actionAnnouncementListFragmentToAnnouncementDetailsFragment( id = petPhoto.id.toString() )
            holder.itemView.findNavController().navigate(action)
        }
    }
}