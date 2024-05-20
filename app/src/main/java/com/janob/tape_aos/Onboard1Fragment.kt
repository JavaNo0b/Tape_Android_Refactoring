package com.example.tape

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentOnboard1Binding

class Onboard1Fragment : Fragment() {

    lateinit var binding : FragmentOnboard1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboard1Binding.inflate(inflater, container, false)




        return binding.root
    }
}