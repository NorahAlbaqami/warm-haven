package com.norah.albaqami.warmhaven.announcement.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.announcement.adapter.AnnouncementAdapter
import com.norah.albaqami.warmhaven.databinding.FragmentAnnouncementListBinding
import com.norah.albaqami.warmhaven.user.data.LogInActivity


class AnnouncementListFragment : Fragment() {
    private lateinit var binding:FragmentAnnouncementListBinding
    val auth = FirebaseAuth.getInstance().currentUser
    private val AnnViewModel: AnnouncementViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAnnouncementListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.annViewModel = AnnViewModel
        binding?.announcementList?.adapter = AnnouncementAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * it will check if user not authorized it's will redirect him to login activity
         */
        binding.addAnnouncementBtn.setOnClickListener {
            if(auth == null) {
                val intent = Intent(getActivity(), LogInActivity::class.java)
                getActivity()?.startActivity(intent)
            }else{
                Navigation.findNavController(it).navigate(AnnouncementListFragmentDirections.actionAnnouncementListFragmentToAddAnnouncementFragment())

            }
        }
    }
}