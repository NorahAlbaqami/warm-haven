package com.norah.albaqami.warmhaven.pet.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norah.albaqami.warmhaven.databinding.PetCardBinding
import com.norah.albaqami.warmhaven.network.PetItem
import com.norah.albaqami.warmhaven.pet.ui.PetsListFragmentDirections

class PetsAdapter : ListAdapter<PetItem, PetsAdapter.PetViewHolder>(DiffCallback) {
    class PetViewHolder( var binding: PetCardBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(views: PetItem) {
            binding.resultItem = views

            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<PetItem>() {
        override fun areItemsTheSame(oldItem: PetItem, newItem: PetItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PetItem, newItem: PetItem): Boolean {
            return oldItem.image == newItem.image
        }
    }
    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder(
            PetCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }
    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petPhoto = getItem(position)
        petPhoto.id
        holder.bind(petPhoto)
        holder.binding.PetCard.setOnClickListener {
            val action = PetsListFragmentDirections.actionPetsListFragmentToPetDetailsFragment( petId = petPhoto.id.toString() )
            holder.itemView.findNavController().navigate(action)


           // Log.e("TAG", "onBindViewHolder: ${petPhoto.id}", )

        }
    }
}