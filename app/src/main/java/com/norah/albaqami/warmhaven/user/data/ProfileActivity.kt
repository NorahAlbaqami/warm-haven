package com.norah.albaqami.warmhaven.user.data

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    val auth = FirebaseAuth.getInstance().currentUser
    val SHARED = "shared"
    val NAME ="name"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        getProfileImage()
        loadData()
        binding.savename.setOnClickListener {
            saveData()
        }
         getUserEmail()
        getUserNumber()
        binding.logout.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnSuccessListener {
                startActivity(Intent(this,LogInActivity::class.java))
                Toast.makeText(this,"Successfully Logged Out", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun getProfileImage(){
       Glide.with(this)
           .load(auth?.photoUrl)
           .fitCenter()
           .placeholder(R.drawable.paw)
           .into(binding.userimage)
    }
    fun getUserEmail(){
        binding.username.text= auth?.email
    }
    fun getUserNumber(){
        binding.userphone.text= auth?.phoneNumber
    }
    fun loadData(){
        val shared = getSharedPreferences(SHARED , Context.MODE_PRIVATE)
        val name =shared.getString(NAME ,"")
      //  binding.nameInput.text= name.toString()

    }
    fun saveData(){
        val name = binding.nameInput.text.toString()
        val shared = getSharedPreferences(SHARED , Context.MODE_PRIVATE)
        val editor =shared.edit()
        editor.putString(NAME,name)
        editor.apply()
        Toast.makeText(this,"Your Name Successfully Modify", Toast.LENGTH_LONG).show()

    }
}