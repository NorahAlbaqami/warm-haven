package com.norah.albaqami.warmhaven.pet.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.FragmentHomeScreenBinding
import com.norah.albaqami.warmhaven.databinding.FragmentPetsListBinding
import com.norah.albaqami.warmhaven.pet.adapter.PetsAdapter
import com.norah.albaqami.warmhaven.pet.ui.logic.PetViewModel
import com.norah.albaqami.warmhaven.user.data.LogInActivity


class PetsListFragment : Fragment() {
   private lateinit var binding: FragmentPetsListBinding
    val auth = FirebaseAuth.getInstance().currentUser
    private val viewModel: PetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPetsListBinding.inflate(inflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding?.petList?.adapter = PetsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addpetbtn.setOnClickListener {
            if(auth == null) {
                val intent = Intent(getActivity(), LogInActivity::class.java)
                getActivity()?.startActivity(intent)
            }else{
                Navigation.findNavController(it).navigate(PetsListFragmentDirections.actionPetsListFragmentToAddNewPetFragment())

            }
        }
    }

}