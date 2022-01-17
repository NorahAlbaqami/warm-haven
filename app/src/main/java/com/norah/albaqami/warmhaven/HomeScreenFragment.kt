package com.norah.albaqami.warmhaven

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.norah.albaqami.warmhaven.databinding.FragmentHomeScreenBinding
import com.norah.albaqami.warmhaven.user.data.LogInActivity
import com.norah.albaqami.warmhaven.user.data.ProfileActivity
import com.norah.albaqami.warmhaven.user.ui.UserPetsViewModel


class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    val auth = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    val userVieModel:UserPetsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener {
            if(auth == null){
            val intent = Intent (getActivity(), LogInActivity::class.java)
            getActivity()?.startActivity(intent)
        }else{
                val intent = Intent (getActivity(), ProfileActivity::class.java)
                getActivity()?.startActivity(intent)
            }
        }
userVieModel.getUserPets(FirebaseAuth.getInstance().uid?:"")

     binding.pets.setOnClickListener { view :View ->
         Navigation.findNavController(view).navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToPetsListFragment())
     }
        binding.announceNav.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToAnnouncementListFragment())
        }



    }





}