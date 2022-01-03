package com.norah.albaqami.warmhaven.pet.ui

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.pet.data.PetItem


class AddNewPetFragment : Fragment() {
    private lateinit var binding: FragmentAddNewPetBinding
    val auth = FirebaseAuth.getInstance().currentUser
    val db = FirebaseDatabase.getInstance()
   val  mRef =db.getReference("pet")
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
            val intent =Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1992)
        }
            binding.btnAdd.setOnClickListener { addNewPet()
                Navigation.findNavController(it).navigate(AddNewPetFragmentDirections.actionAddNewPetFragmentToPetsListFragment())

        }

    }
    override fun onResume() {
        super.onResume()
        val pets = resources.getStringArray(R.array.pets)
        val arrayAdopter = ArrayAdapter(requireContext(), R.layout.drobdown_items,pets)
        binding.autoCompleteTextView.setAdapter(arrayAdopter)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    fun addNewPet(){
        val type = binding.autoCompleteTextView.text.toString()
        val name = binding.nameInput.text.toString()
        val location = binding.locationInput.text.toString()
        val phone = binding.phoneInput.text.toString()
        val description = binding.descriptionInput.text.toString()
       val imageLink= binding.linkInput.text.toString()
       if (type.isNotEmpty() && name.isNotEmpty() && location.isNotEmpty() && phone.isNotEmpty() && description.isNotEmpty() && imageLink.isNotEmpty()){
       val id = mRef.push().key
val userId = auth?.uid
           var newPet = PetItem(imageLink , phone , name , description , location ,id ,type , userId)
           db.getReference("pet/$id").setValue(newPet)
           //mRef.child(id!!).setValue(newPet)
       }else {
          // Toast.makeText(this,"Make sure to full all fields", Toast.LENGTH_LONG).show()
       }
    }
}