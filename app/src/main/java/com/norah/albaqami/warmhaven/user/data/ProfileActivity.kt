package com.norah.albaqami.warmhaven.user.data

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.HomeScreenFragmentDirections
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.ActivityProfileBinding
import com.norah.albaqami.warmhaven.user.ui.UserAnnouncementActivity
import com.norah.albaqami.warmhaven.user.ui.UserPetsActivity
import java.util.*


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    val auth = FirebaseAuth.getInstance().currentUser
    val SHARED = "shared"
    val NAME ="name"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
      binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.baby_pink))
        }
        getProfileImage()
        loadData()
        binding.savename.setOnClickListener {
            saveData()
        }
        binding.lang.setOnClickListener { showChangeLang() }
         getUserEmail()
        getUserNumber()
        binding.logout.setOnClickListener {
            AuthUI.getInstance().signOut(this).addOnSuccessListener {
              val logoutIntent  = (Intent(this,LogInActivity::class.java))
                logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(logoutIntent)
                Toast.makeText(this,"Successfully Logged Out", Toast.LENGTH_LONG).show()
            }

        }
      binding.userpets.setOnClickListener {
      val intent = Intent(this,UserPetsActivity::class.java)
          startActivity(intent)
      }
        binding.announce.setOnClickListener {
            val intent = Intent(this,UserAnnouncementActivity::class.java)
            startActivity(intent)
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

    fun saveData(){
        var name = binding.nameInput.text.toString()
        val shared = getSharedPreferences(SHARED , Context.MODE_PRIVATE)
        val editor =shared.edit()
        editor.putString(NAME,name)
        editor.apply()

        Toast.makeText(this,"Your Name Successfully Modify", Toast.LENGTH_LONG).show()

    }
    fun loadData(){
        val shared = getSharedPreferences(SHARED , Context.MODE_PRIVATE)
        var name =shared.getString(NAME ,"")

        binding.nameInput.setText(name)

    }

    private fun showChangeLang() {

        val listItmes = arrayOf("عربي", "English")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(getString(R.string.Choose_Language))
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
            if (which == 0) {
                setLocate("ar")
                recreate()
            } else if (which == 1) {
                setLocate("en")
                recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }
    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if(language != null) {
            setLocate(language)
        }
    }
}