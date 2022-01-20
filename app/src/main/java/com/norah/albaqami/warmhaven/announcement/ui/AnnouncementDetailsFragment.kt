package com.norah.albaqami.warmhaven.announcement.ui

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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.binding.bindImage
import com.norah.albaqami.warmhaven.databinding.FragmentAnnouncementDetailsBinding
import com.norah.albaqami.warmhaven.network.AnnouncementItem
import com.norah.albaqami.warmhaven.network.PetItem

lateinit var announcementId : String
class AnnouncementDetailsFragment : Fragment() {
    private var _binding: FragmentAnnouncementDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            announcementId = it?.getString("id").toString()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnnouncementDetailsBinding.inflate(inflater,container,false)
        binding?.lifecycleOwner = this
        return binding!!.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDetails(announcementId)
    }
    /*Description : Function for Show announcement details.
    * Returns : Nothing
    * Parameters : arg to pass announcement id
    */
    private fun getDetails(arg: String) {
        val db = FirebaseDatabase.getInstance()
        val mRef = db.getReference("announcement").child(arg)
        mRef.get().addOnCompleteListener { DataSnapshot1 ->
            val details = DataSnapshot1.result.getValue(AnnouncementItem::class.java)
            binding.titleDetail.text = details?.title.toString()
            bindImage(binding.petPictureDetail, details?.image)
            binding.petDescription.text = details?.description.toString()
            binding.petLocation.text = details?.location.toString()

            binding.callBtn.setOnClickListener {
            var phoneNumber = details?.phone.toString().trim()
            if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Please Grand Permission for phone call ", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phoneNumber)))
                startActivity(intent)
            }
            }
            binding.executePendingBindings()
        }

        }

}