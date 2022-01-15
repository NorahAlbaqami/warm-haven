package com.norah.albaqami.warmhaven.announcement.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.databinding.FragmentAddAnnouncementBinding
import com.norah.albaqami.warmhaven.network.AnnouncementItem


class AddAnnouncementFragment : Fragment() {
    private lateinit var binding: FragmentAddAnnouncementBinding
    val auth = FirebaseAuth.getInstance().currentUser
    val db = FirebaseDatabase.getInstance()
    val mRef = db.getReference("announcement")

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
        binding.btnAdd.setOnClickListener { addAnnounce() }
    }
    fun isValid(): Boolean {

        return  binding.descriptionContainer.isValid()
                && binding.phoneContainer.isValid()
                && binding.locationContainer.isValid()
                && binding.phoneContainer.isValid()
                && binding.imageLinkContainer.isValid()
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
    fun addAnnounce(){
        if (isValid()){
            val title = binding.titleInput.text.toString()
            val location = binding.locationInput.text.toString()
            val phone = binding.phoneInput.text.toString()
            val description = binding.descriptionInput.text.toString()
            val imageLink = binding.linkInput.text.toString()
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
            binding.imageLinkContainer.helperText = "Please full this failed"
        }
    }
}