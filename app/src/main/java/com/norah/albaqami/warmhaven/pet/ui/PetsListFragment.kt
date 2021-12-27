package com.norah.albaqami.warmhaven.pet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.FragmentHomeScreenBinding
import com.norah.albaqami.warmhaven.databinding.FragmentPetsListBinding


class PetsListFragment : Fragment() {
   private lateinit var binding: FragmentPetsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPetsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addpetbtn.setOnClickListener {
Navigation.findNavController(it).navigate(PetsListFragmentDirections.actionPetsListFragmentToAddNewPetFragment())
        }
    }

}