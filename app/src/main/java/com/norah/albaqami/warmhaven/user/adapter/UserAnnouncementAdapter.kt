package com.norah.albaqami.warmhaven.user.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.UserAnnouncementCardBinding
import com.norah.albaqami.warmhaven.network.AnnouncementItem
import com.norah.albaqami.warmhaven.network.PetItem
import com.norah.albaqami.warmhaven.user.ui.EditUserAnnouncementActivity
import com.norah.albaqami.warmhaven.user.ui.EditUserPetsActivity

class UserAnnouncementAdapter(val context: Context, val onDeleteClickListener: (AnnouncementItem) -> Unit) :
    ListAdapter<AnnouncementItem, UserAnnouncementAdapter.AnnouncementViewHolder>(
        DiffCallback
    ) {
    class AnnouncementViewHolder(var binding: UserAnnouncementCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(views: AnnouncementItem) {
            binding.resultItem = views
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AnnouncementItem>() {
        override fun areItemsTheSame(
            oldItem: AnnouncementItem,
            newItem: AnnouncementItem
        ): Boolean {
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
            MaterialAlertDialogBuilder(context, position)
                .setTitle(R.string.delete)
                .setMessage(R.string.Are_you_Sure_to_delete)
                .setCancelable(true)
                .setNegativeButton(context.getString(R.string.no)) { _, _ -> }
                .setPositiveButton(context.getString(R.string.delete)) { _, _ ->
                    onDeleteClickListener(petPhoto)
                }
                .show()
        }
        holder.binding.editPet.setOnClickListener {
            var intent: Intent = Intent(context, EditUserAnnouncementActivity::class.java)
            intent.putExtra("idAnn", petPhoto.id.toString())
            context.startActivity(intent)
        }
    }



}