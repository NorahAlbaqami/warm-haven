package com.norah.albaqami.warmhaven.pet.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.google.firebase.database.FirebaseDatabase
import com.norah.albaqami.warmhaven.databinding.FragmentPetDetailsBinding
import com.norah.albaqami.warmhaven.pet.ui.logic.PetViewModel


class PetDetailsFragment : Fragment() {
    private var _binding : FragmentPetDetailsBinding? = null
    private val binding get() = _binding
    private val viewModel: PetViewModel by viewModels()
    //lateinit var a : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments.let {
////            viewModel.setPetDetails(it?.getInt("petId")!!)
////            Log.d("meme", "onCreateView:${viewModel.setPetDetails(it?.getInt("petIndex")!!)} ")
//            a = it?.getString("petId").toString()
//
//        }
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

        //   getD(a)
    }
}
//    private fun getD(arg : String) {
//        var x = PetItem("")
//        Log.e("TAG", "onViewCreated: $arg", )
//        val db = FirebaseDatabase.getInstance()
//        val mRef = db.getReference("pet").child(arg)
//        Log.e("TAG", "onViewCreated: $mRef", )
//        mRef.get().addOnCompleteListener { DataSnapshot1->
//           var e = (DataSnapshot1.result.value)
//               toObject(PetItem::class.java)
//
//            Log.e("tata", "onViewCreated: result ${DataSnapshot1.result.value}", )
//            Log.e("tata", "onViewCreated:  DataSnapshot1 ${DataSnapshot1}", )
//    }

//}