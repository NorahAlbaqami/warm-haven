package com.norah.albaqami.warmhaven.user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.ActivityUserPetsBinding
import com.norah.albaqami.warmhaven.pet.adapter.PetsAdapter
import com.norah.albaqami.warmhaven.user.adapter.UserPetsAdapter

class UserPetsActivity : AppCompatActivity() {

    private val viewModel: UserPetsViewModel by viewModels()
    private lateinit var binding: ActivityUserPetsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_pets)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding?.petList?.adapter = UserPetsAdapter(this) {

            deletePet(it.id!!)
        }
        setContentView(binding.root)
    }
    fun deletePet(id: String) {
        var db = FirebaseDatabase.getInstance().getReference("pet")
        db.child(id).removeValue().addOnSuccessListener {
            Toast.makeText(this, getString(R.string.Deleted_Successfully), Toast.LENGTH_SHORT).show()
            viewModel.getUserPets()
        }.addOnFailureListener {
            Toast.makeText(this, getString(R.string.An_error_occurred), Toast.LENGTH_SHORT).show()
        }

    }
}