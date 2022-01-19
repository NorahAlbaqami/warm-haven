package com.norah.albaqami.warmhaven.user.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.UserPetCardBinding
import com.norah.albaqami.warmhaven.network.PetItem
import com.norah.albaqami.warmhaven.user.ui.EditUserPetsActivity
import com.norah.albaqami.warmhaven.user.ui.UserPetsActivity
import com.norah.albaqami.warmhaven.user.ui.UserPetsViewModel
import com.norah.albaqami.warmhaven.user.adapter.UserPetsAdapter.DiffCallback as DiffCallback1


class UserPetsAdapter(val context: Context, val onDeleteClickListener: (PetItem) -> Unit) :
    ListAdapter<PetItem, UserPetsAdapter.PetViewHolder>(DiffCallback1) {

    class PetViewHolder(var binding: UserPetCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(views: PetItem) {
            binding.resultItem = views

            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PetItem>() {
        override fun areItemsTheSame(oldItem: PetItem, newItem: PetItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PetItem, newItem: PetItem): Boolean {
            return oldItem.image == newItem.image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder(
            UserPetCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petPhoto = getItem(position)
        holder.bind(petPhoto)
        petPhoto.id
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
            var intent: Intent = Intent(context, EditUserPetsActivity::class.java)
            intent.putExtra("id", petPhoto.id.toString())
            context.startActivity(intent)
        }
    }


}