package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    lateinit var binding : FragmentSettingBinding

    lateinit var my_user : User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)

        // init
        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)

        return binding.root
    }
}