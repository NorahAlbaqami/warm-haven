package com.norah.albaqami.warmhaven.pet.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.FragmentAddNewPetBinding
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.norah.albaqami.warmhaven.pet.data.PetItem


class AddNewPetFragment : Fragment() {

    private lateinit var binding: FragmentAddNewPetBinding

    val auth = FirebaseAuth.getInstance().currentUser
    val db = FirebaseDatabase.getInstance()
    val mRef = db.getReference("pet")

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = FirebaseStorage.getInstance()
    private var storageReference: StorageReference? = FirebaseStorage.getInstance().reference

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
        TODO("Not yet implemented")
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
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
}