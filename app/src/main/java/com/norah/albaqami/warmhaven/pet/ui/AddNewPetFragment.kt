package com.norah.albaqami.warmhaven.pet.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.FragmentAddNewPetBinding
import com.norah.albaqami.warmhaven.databinding.FragmentPetsListBinding


class AddNewPetFragment : Fragment() {
    private lateinit var binding: FragmentAddNewPetBinding

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


    }

    override fun onResume() {
        super.onResume()
        val pets = resources.getStringArray(R.array.pets)
        val arrayAdopter = ArrayAdapter(requireContext(), R.layout.drobdown_items,pets)
        binding.autoCompleteTextView.setAdapter(arrayAdopter)
    }
}