package com.norah.albaqami.warmhaven.pet.binding

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.pet.adapter.PetsAdapter
import com.norah.albaqami.warmhaven.pet.data.PetItem
import com.norah.albaqami.warmhaven.pet.ui.logic.PetApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide
            .with(imgView.context)
            .load("${imgUrl}")
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(imgView)
        Log.d("nana", "bindImage: ${imgUrl}")
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
@BindingAdapter("apiStatus")
fun ImageView.bindStatus(status: PetApiStatus){
    when (status){
        PetApiStatus.LOADING -> {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.loading_animation)
        }
        PetApiStatus.ERROR -> {
            this.visibility = View.VISIBLE
            this.setImageResource(R.drawable.ic_connection_error)
        }

        PetApiStatus.DONE -> {
            this.visibility = View.GONE

        }
    }


}