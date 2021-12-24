package com.norah.albaqami.warmhaven.user.data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    val auth = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.username.text= auth?.displayName
        binding.logout.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnSuccessListener {
                startActivity(Intent(this,LogInActivity::class.java))
                Toast.makeText(this,"Successfully Logged Out", Toast.LENGTH_LONG).show()
            }
        }
    }
}