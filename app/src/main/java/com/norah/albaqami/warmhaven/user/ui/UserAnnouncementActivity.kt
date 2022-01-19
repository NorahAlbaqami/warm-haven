package com.norah.albaqami.warmhaven.user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.ActivityUserAnnouncementBinding
import com.norah.albaqami.warmhaven.user.adapter.UserAnnouncementAdapter


class UserAnnouncementActivity : AppCompatActivity() {
    private val viewModel: UserPetsViewModel by viewModels()
    private lateinit var binding: ActivityUserAnnouncementBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_announcement)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding?.petList?.adapter = UserAnnouncementAdapter(this){
            deleteAnnouncement(it.id!!)
        }
        setContentView(binding.root)
    }

    fun deleteAnnouncement(id: String) {
        var db = FirebaseDatabase.getInstance().getReference("announcement")
        db.child(id).removeValue().addOnSuccessListener {
            Toast.makeText(this, getString(R.string.Deleted_Successfully), Toast.LENGTH_SHORT).show()
            viewModel.getUserAnnouncements()
        }.addOnFailureListener {
            Toast.makeText(this, getString(R.string.An_error_occurred), Toast.LENGTH_SHORT).show()

        }

    }
}