package com.norah.albaqami.warmhaven.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.databinding.UserAnnouncementCardBinding
import com.norah.albaqami.warmhaven.network.AnnouncementItem

class UserAnnouncementAdapter : ListAdapter<AnnouncementItem, UserAnnouncementAdapter.AnnouncementViewHolder>(
    DiffCallback
)  {
    class AnnouncementViewHolder( var binding: UserAnnouncementCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(views: AnnouncementItem) {
            binding.resultItem= views
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
            UserAnnouncementCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        val petPhoto = getItem(position)
        holder.bind(petPhoto)
        petPhoto.id.toString()
        holder.binding.deletePet.setOnClickListener {
            deleteAnnouncement(petPhoto.id.toString())
        }
    }
    fun deleteAnnouncement(id : String){
        var db = FirebaseDatabase.getInstance().getReference("announcement")
        db.child(id).removeValue().addOnSuccessListener {
            //  Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {

        }

    }

}