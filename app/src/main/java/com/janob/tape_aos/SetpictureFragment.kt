package com.example.tape

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentSetpictureBinding

class SetpictureFragment : Fragment() {

    lateinit var binding : FragmentSetpictureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetpictureBinding.inflate(inflater, container, false)

        return binding.root
    }
}