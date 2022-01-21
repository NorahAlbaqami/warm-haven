package com.norah.albaqami.warmhaven.user.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.binding.bindImage
import com.norah.albaqami.warmhaven.databinding.ActivityEditUserPetsBinding
import com.norah.albaqami.warmhaven.network.PetItem
import java.io.IOException

class EditUserPetsActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance().currentUser
    val db = FirebaseDatabase.getInstance()
    private var storageReference: StorageReference? =
        FirebaseStorage.getInstance().reference.child("pet")
    var id = ""
    var FilePathUri: Uri? = null
    val mRef = db.getReference("pet")
    private lateinit var binding: ActivityEditUserPetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_pets)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.baby_pink))
        }
        binding.selectPetImage.setOnClickListener {
            launchGallery()
        }
        binding.btnSave.setOnClickListener {
            uploadImage()
        }
        id = intent.getStringExtra("id").toString()
        getDetails(id)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 50 && resultCode == RESULT_OK && data != null && data.data != null) {
            FilePathUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    getContentResolver(),
                    FilePathUri
                )
                binding.petPic.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        var imageName: StorageReference =
            storageReference!!.child("pet" + FilePathUri!!.getLastPathSegment())

        val upload = imageName.putFile(FilePathUri!!)
        val urlTask = upload.continueWith { task ->
            if(!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageName.downloadUrl
        }.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                task.result.addOnCompleteListener {
                    updatePet(id!!, it.result.toString())
                }

            } else {
                // Handle failures
                // ...
            }
        }

    }

    private fun getDetails(arg: String) {
        val db = FirebaseDatabase.getInstance()
        val mRef = db.getReference("pet").child(arg)

        mRef.get().addOnCompleteListener { DataSnapshot1 ->
            val petdetails = DataSnapshot1.result.getValue(PetItem::class.java)
            binding.descriptionInput.setText(petdetails?.description.toString())
            bindImage(binding.petPic, petdetails?.image)
            binding.nameInput.setText(petdetails?.name.toString())
            binding.locationInput.setText(petdetails?.location.toString())
            binding.phoneInput.setText(petdetails?.phone.toString())
        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Document to upload"),
            50
        )
    }

    /*Description : Function edit pet .
         * Returns : Nothing
         * Parameters : idEdit to pass pet id , imageLin to pass image link from uploadImage()
         */
    fun updatePet(idEdit: String, imageLin: String) {
        val type = binding.autoCompleteTextView.text.toString()
        val name = binding.nameInput.text.toString()
        val location = binding.locationInput.text.toString()
        val phone = binding.phoneInput.text.toString()
        val description = binding.descriptionInput.text.toString()
        val userId = auth?.uid
        val editPet = PetItem(imageLin, phone, name, description, location, idEdit, type, userId)
        db.getReference("pet/$idEdit").setValue(editPet).addOnCompleteListener {
            Toast.makeText(this, "Edit it successfully", Toast.LENGTH_SHORT).show()
        }
    }
}