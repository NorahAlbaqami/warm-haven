package com.norah.albaqami.warmhaven.user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.norah.albaqami.warmhaven.R
import com.norah.albaqami.warmhaven.databinding.ActivityUserPetsBinding

class UserPetsActivity : AppCompatActivity() {
    private val viewModel: UserPetsViewModel by viewModels()
    private lateinit var binding: ActivityUserPetsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_pets)
        binding.vm=viewModel
    }
}