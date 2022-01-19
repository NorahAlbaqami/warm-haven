package com.norah.albaqami.warmhaven.user.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.UserPetCardBinding
import com.norah.albaqami.warmhaven.network.PetItem


class UserPetsAdapter : ListAdapter<PetItem, UserPetsAdapter.PetViewHolder>(DiffCallback) {
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
            deletePet(petPhoto.id.toString())
//            MaterialAlertDialogBuilder(requireContext(),position)
//                .setTitle(R.string.delete)
//                .setMessage(R.string.Are_you_Sure_to_delete)
//                .setCancelable(false)
//                .setNegativeButton(requireContext().getString(R.string.delete)) { _, _ ->
//                    deletePet(petPhoto.id.toString())

//                }
//                .show()

        }
    }

    fun deletePet(id: String) {

        var db = FirebaseDatabase.getInstance().getReference("pet")
        db.child(id).removeValue().addOnSuccessListener {
            //  Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show()
           // notifyDataSetChanged()
        }.addOnFailureListener {

        }

        // Log.e("TAG", "deletePet: end", )
    }
}