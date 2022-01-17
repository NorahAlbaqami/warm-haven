package com.norah.albaqami.warmhaven.pet.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.FragmentAddNewPetBinding
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.norah.albaqami.warmhaven.databinding.FragmentAddAnnouncementBinding
import com.norah.albaqami.warmhaven.network.PetItem
import java.io.IOException


class AddNewPetFragment : Fragment() {

    private lateinit var binding: FragmentAddNewPetBinding

    val auth = FirebaseAuth.getInstance().currentUser
    val db = FirebaseDatabase.getInstance()
    val mRef = db.getReference("pet")
    private var storageReference: StorageReference? = FirebaseStorage.getInstance().reference.child("pet")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectPetImage.setOnClickListener {
              launchGallery()
        }

        binding.uploadPetImage.setOnClickListener {
            uploadImage()
        }
        binding.btnAdd.setOnClickListener {
            addNewPet()
        }

    }

    private fun uploadImage() {

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

    override fun onResume() {
        super.onResume()
        val pets = resources.getStringArray(R.array.pets)
        val arrayAdopter = ArrayAdapter(requireContext(), R.layout.drobdown_items, pets)
        binding.autoCompleteTextView.setAdapter(arrayAdopter)
    }

    fun isValid(): Boolean {

        return binding.NameContainer.isValid()
                && binding.descriptionContainer.isValid()
                && binding.phoneContainer.isValid()
                && binding.locationContainer.isValid()
                && binding.phoneContainer.isValid()
                && binding.imageLinkContainer.isValid()
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

    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 50 && resultCode == RESULT_OK && data != null && data.data != null) {
            var FilePathUri = data.data
            var imageName : StorageReference = storageReference!!.child("pet"+FilePathUri!!.getLastPathSegment())
            imageName.putFile(FilePathUri).addOnSuccessListener ({ taskSnapShot ->
               imageName.getDownloadUrl().addOnSuccessListener { OnSuccessListener<Uri>{

               } }

            } )

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
    fun addNewPet() {
        if (isValid()){
        val type = binding.autoCompleteTextView.text.toString()
        val name = binding.nameInput.text.toString()
        val location = binding.locationInput.text.toString()
        val phone = binding.phoneInput.text.toString()
        val description = binding.descriptionInput.text.toString()
        val imageLink = binding.linkInput.text.toString()
               val id = mRef.push().key
            val userId = auth?.uid
            val newPet = PetItem(imageLink, phone, name, description, location, id, type, userId)
            db.getReference("pet/$id").setValue(newPet).addOnCompleteListener {
                if(it.isSuccessful){
               findNavController().navigate(AddNewPetFragmentDirections.actionAddNewPetFragmentToPetsListFragment())
                }
            }
        } else {
            binding.NameContainer.helperText = "Please full this failed"
            binding.locationContainer.helperText = "Please full this failed"
            binding.phoneContainer.helperText = "Please full this failed"
            binding.descriptionContainer.helperText = "Please full this failed"
            binding.imageLinkContainer.helperText = "Please full this failed"
        }
    }
//    fun uploadDataToFirebase(fileUri: Uri?, binding: FragmentAddAnnouncementBinding) {
//        if (fileUri != null) {
//
//            val storageReference2nd = storageReference!!.child(
//                Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(fileUri)
//            )
//            storageReference2nd.putFile(fileUri)
//                .addOnSuccessListener { taskSnapshot ->
//                    val addOnSuccessListener =
//                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
//
//                            val imageUrl = it.toString()
//                            addNewPet(imageUrl, binding)
//                        }
//                }
//
//                .addOnFailureListener(OnFailureListener { e ->
//                    //  Toast.makeText(getApplication(),e.message.toString(),Toast.LENGTH_LONG).show()
//
//                })
//
//
//        }
//
//
//    }
}