package com.norah.albaqami.warmhaven.user.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.binding.bindImage
import com.norah.albaqami.warmhaven.databinding.ActivityEditUserAnnouncementBinding
import com.norah.albaqami.warmhaven.network.AnnouncementItem
import java.io.IOException

class EditUserAnnouncementActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance().currentUser
    val db = FirebaseDatabase.getInstance()
    private var storageReference: StorageReference? =
        FirebaseStorage.getInstance().reference.child("announcement")
    var idAnnounce = ""
    var FilePathUri: Uri? = null

    private lateinit var binding:ActivityEditUserAnnouncementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_user_announcement)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.baby_pink))
        }
        idAnnounce = intent.getStringExtra("idAnn").toString()
        binding.selectPetImage.setOnClickListener {
            launchGallery()
        }
        binding.btnEdit.setOnClickListener {
            if(validationHasPhoto()){
                uploadImage()
            }else{
            Toast.makeText(this, getString(R.string.selecr_an_image_please), Toast.LENGTH_SHORT).show()
}
        }
        getDetails(idAnnounce)
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
        var imageName: StorageReference = storageReference!!.child("announcement" + FilePathUri!!.getLastPathSegment())
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
                    updateAnnouncement(idAnnounce!!, it.result.toString())
                }

            } else {
                // Handle failures
                // ...
            }
        }

    }

    /**
     *
     */
    private fun getDetails(arg: String) {
        val mRef = db.getReference("announcement").child(arg)
        mRef.get().addOnCompleteListener { DataSnapshot1 ->
            val details = DataSnapshot1.result.getValue(AnnouncementItem::class.java)
            binding.descriptionInput.setText(details?.description.toString())
            bindImage(binding.petPic, details?.image)
            binding.titleInput.setText(details?.title.toString())
            binding.locationInput.setText(details?.location.toString())
            binding.phoneInput.setText(details?.phone.toString())

        }

    }
    /*Description : Function edit announcement .
         * Returns : Nothing
         * Parameters : idEdit to pass announcement id , imageLin to pass image link from uploadImage()
         */
    fun updateAnnouncement(idEdit: String, imageLin: String) {
        val title = binding.titleInput.text.toString()
        val location = binding.locationInput.text.toString()
        val phone = binding.phoneInput.text.toString()
        val description = binding.descriptionInput.text.toString()
        val userId = auth?.uid
        val editAnnouncement = AnnouncementItem(idEdit, userId , title, description, location, imageLin , phone  )
        db.getReference("announcement/$idEdit").setValue(editAnnouncement).addOnCompleteListener {
            Toast.makeText(this, "Edit it successfully", Toast.LENGTH_SHORT).show()
        }
    }
    fun validationHasPhoto():Boolean{
        var result = true
        if (FilePathUri == null){
            result = false
        }else true

        return result
    }
}