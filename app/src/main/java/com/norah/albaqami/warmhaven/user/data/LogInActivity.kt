package com.norah.albaqami.warmhaven.user.data

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.R

class LogInActivity : AppCompatActivity() {
    companion object{
        private const val RC_SIGN =1992
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        loginOptions()
    }
    fun loginOptions(){
        val providers = arrayListOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.LoginUi)
                .setLogo(R.drawable.cat)
                .build(),
                RC_SIGN

        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN){
            var response = IdpResponse.fromResultIntent(data)
            if (requestCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                //Log.d("TAG", "${user?.displayName}")
               startActivity(Intent(this,ProfileActivity::class.java))
            }else{
                if (response == null){
                    finish()
                }
                if (response?.error?.errorCode == ErrorCodes.NO_NETWORK){
                    Toast.makeText(this,response?.error?.errorCode.toString(),Toast.LENGTH_LONG).show()
                    return
                }
                if (response?.error?.errorCode == ErrorCodes.UNKNOWN_ERROR){
                    Toast.makeText(this,response?.error?.errorCode.toString(),Toast.LENGTH_LONG).show()
                    return
                }
            }

        }
    }
}