package com.norah.albaqami.warmhaven.pet.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.binding.bindImage
import com.norah.albaqami.warmhaven.databinding.FragmentPetDetailsBinding
import com.norah.albaqami.warmhaven.network.PetItem
import com.norah.albaqami.warmhaven.pet.ui.logic.PetViewModel


class PetDetailsFragment : Fragment() {
    private var _binding: FragmentPetDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PetViewModel by viewModels()
    lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            id = it?.getString("petId").toString()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPetDetailsBinding.inflate(inflater, container, false)
        // binding.viewModel = viewModel
        binding?.lifecycleOwner = this

        return binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetails(id)
    }

    /*Description : Function for Show pet details.
        * Returns : Nothing
        * Parameters : arg to pass pet id
        */
    private fun getDetails(arg: String) {
        val db = FirebaseDatabase.getInstance()
        val mRef = db.getReference("pet").child(arg)

        mRef.get().addOnCompleteListener { DataSnapshot1 ->
            val petdetails = DataSnapshot1.result.getValue(PetItem::class.java)
            binding.petDescription.text = petdetails?.description.toString()
            bindImage(binding.petPictureDetail, petdetails?.image)
            binding.petLocation.text = petdetails?.location.toString()
            binding.petNameDetail.text = petdetails?.name.toString()
            binding.callBtn.setOnClickListener {

                var phoneNumber = petdetails?.phone.toString().trim()
                if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireContext(), getString(R.string.permission), Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phoneNumber)))
                    startActivity(intent)
                }

            }
            binding.executePendingBindings()

        }

    }
}