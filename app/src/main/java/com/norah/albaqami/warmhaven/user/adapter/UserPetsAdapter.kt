package com.norah.albaqami.warmhaven.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norah.albaqami.warmhaven.databinding.UserPetCardBinding
import com.norah.albaqami.warmhaven.network.PetItem


class UserPetsAdapter : ListAdapter<PetItem,UserPetsAdapter.PetViewHolder>(DiffCallback) {
    class PetViewHolder( var binding: UserPetCardBinding): RecyclerView.ViewHolder(binding.root) {

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
        return PetViewHolder (
                UserPetCardBinding.inflate(LayoutInflater.from(parent.context))
                )
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petPhoto = getItem(position)
        holder.bind(petPhoto)
    }
}