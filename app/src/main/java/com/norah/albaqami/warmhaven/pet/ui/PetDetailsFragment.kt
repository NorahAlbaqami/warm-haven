package com.norah.albaqami.warmhaven.pet.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.norah.albaqami.warmhaven.databinding.FragmentPetDetailsBinding
import com.norah.albaqami.warmhaven.pet.ui.logic.PetViewModel


class PetDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPetDetailsBinding
    private val viewModel: PetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPetDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            viewModel.setPetDetails(it?.getInt("petId")!!)
            Log.d("meme", "onCreateView:${viewModel.setPetDetails(it?.getInt("petIndex")!!)} ")
        }
    }

}