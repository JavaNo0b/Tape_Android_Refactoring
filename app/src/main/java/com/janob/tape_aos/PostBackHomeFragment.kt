package com.janob.tape_aos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostBackHomeBinding

class PostBackHomeFragment : Fragment() {

    interface PostBackToHomeListener { fun onPostAllCompleted() }
    lateinit var listener : PostBackToHomeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is PostBackToHomeListener){
            listener = parentFragment as PostBackToHomeListener
        }else{
            throw Exception("인터페이스 미구현 ")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentPostBackHomeBinding.inflate(inflater,container,false)

        binding.btnToHome.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
        return binding.root
    }
}