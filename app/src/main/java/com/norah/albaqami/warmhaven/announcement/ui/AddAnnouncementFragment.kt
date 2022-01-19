package com.norah.albaqami.warmhaven.announcement.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.norah.albaqami.warmhaven.databinding.FragmentAddAnnouncementBinding
import com.norah.albaqami.warmhaven.network.AnnouncementItem
import java.io.IOException


class AddAnnouncementFragment : Fragment() {
    private lateinit var binding: FragmentAddAnnouncementBinding
    val auth = FirebaseAuth.getInstance().currentUser
    val db = FirebaseDatabase.getInstance()
    val mRef = db.getReference("announcement")
    private var storageReference: StorageReference? =
        FirebaseStorage.getInstance().reference.child("announcement")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddAnnouncementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener { uploadImage() }
        binding.selectPetImage.setOnClickListener {
            launchGallery()
        }
        binding.btnAdd.setOnClickListener {
            uploadImage()

        }
    }
    fun isValid(): Boolean {

        return  binding.descriptionContainer.isValid()
                && binding.phoneContainer.isValid()
                && binding.locationContainer.isValid()
                && binding.phoneContainer.isValid()
                && binding.titleContainer.isValid()
    }

    fun TextInputLayout.isValid(): Boolean {
        if(this.editText?.text.toString().isNullOrEmpty()) {
            this.error=""
            return false
        } else {
            this.error=null
            return true
        }

    }
    fun addAnnounce( imageLink :String){
        if (isValid()){
            val title = binding.titleInput.text.toString()
            val location = binding.locationInput.text.toString()
            val phone = binding.phoneInput.text.toString()
            val description = binding.descriptionInput.text.toString()

            val id = mRef.push().key
            val userId = auth?.uid
            val newAnnouncement = AnnouncementItem(id, userId,title, description, location, imageLink, phone)
            db.getReference("announcement/$id").setValue(newAnnouncement).addOnCompleteListener {
                if(it.isSuccessful){
                    findNavController().navigate(AddAnnouncementFragmentDirections.actionAddAnnouncementFragmentToAnnouncementListFragment())
                }
            }
        } else {
            binding.titleContainer.helperText = "Please full this failed"
            binding.locationContainer.helperText = "Please full this failed"
            binding.phoneContainer.helperText = "Please full this failed"
            binding.descriptionContainer.helperText = "Please full this failed"

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
    var FilePathUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 50 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            FilePathUri = data.data


            try {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().getContentResolver(),
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
            storageReference!!.child("announcement" + FilePathUri!!.getLastPathSegment())

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
                    addAnnounce(it.result.toString())
                }

            } else {
                // Handle failures
                // ...
            }
        }

    }
}