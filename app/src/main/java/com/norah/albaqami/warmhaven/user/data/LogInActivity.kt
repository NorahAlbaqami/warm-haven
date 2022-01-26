package com.norah.albaqami.warmhaven.user.data


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.R

class LogInActivity : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        loginOptions()


    }
   private fun loginOptions(){
        val providers = arrayListOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

       // Create and launch sign-in intent
       val signInIntent = AuthUI.getInstance()
           .createSignInIntentBuilder()
           .setAvailableProviders(providers)
           .setIsSmartLockEnabled(false)
           .setTheme(R.style.LoginUi)
           .setLogo(R.drawable.cat)
           .build()
       signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
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
