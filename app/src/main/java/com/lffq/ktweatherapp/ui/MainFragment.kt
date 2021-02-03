package com.lffq.ktweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lffq.ktweatherapp.R
import com.lffq.ktweatherapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val model: MainFragViewModel = ViewModelProvider(this).get(MainFragViewModel::class.java)
        model.request()
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewmodel = model
        binding.lifecycleOwner = this
        return binding.root
    }
}