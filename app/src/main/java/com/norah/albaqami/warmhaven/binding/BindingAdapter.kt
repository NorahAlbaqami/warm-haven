package com.norah.albaqami.warmhaven.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.announcement.adapter.AnnouncementAdapter
import com.norah.albaqami.warmhaven.network.AnnouncementItem
import com.norah.albaqami.warmhaven.pet.adapter.PetsAdapter
import com.norah.albaqami.warmhaven.network.PetItem
import com.norah.albaqami.warmhaven.pet.ui.logic.ApiStatus
import com.norah.albaqami.warmhaven.user.adapter.UserAnnouncementAdapter
import com.norah.albaqami.warmhaven.user.adapter.UserPetsAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide
            .with(imgView.context)
            .load("${imgUrl}")
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(imgView)

    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PetItem>?){
    if(recyclerView.adapter == null){
        recyclerView.adapter = PetsAdapter()
    }
    val adapter = recyclerView.adapter as PetsAdapter
    adapter.submitList(data)
}

@BindingAdapter("userPets")
 fun bindUserPets(recyclerView: RecyclerView, data: List<PetItem>?){

    val adapter = recyclerView.adapter as UserPetsAdapter
    adapter.submitList(data)

}

@JvmName("bindRecyclerView1")
@BindingAdapter("announcementData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<AnnouncementItem>?){
    if(recyclerView.adapter == null){
        recyclerView.adapter = AnnouncementAdapter()
    }
    val AnnouncementAdapter = recyclerView.adapter as AnnouncementAdapter
    AnnouncementAdapter.submitList(data)
}
@BindingAdapter("userAnnouncementData")
fun bindUserAnnouncement(recyclerView: RecyclerView, data: List<AnnouncementItem>?){
    val UserAnnouncementAdapter = recyclerView.adapter as UserAnnouncementAdapter
    UserAnnouncementAdapter.submitList(data)
}
@BindingAdapter("apiStatus")
fun ImageView.bindStatus(status: ApiStatus){
    when (status){
        ApiStatus.LOADING -> {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.ic_connection_error)
        }

        ApiStatus.DONE -> {
            this.visibility = View.GONE

        }
        ApiStatus.EMPTY -> {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.empty)
        }
    }


}