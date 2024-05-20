package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.janob.tape_aos.databinding.BottomSheetAlbumBinding

class AlbumBottomSheet() : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAlbumBinding.inflate(layoutInflater)

        val sheet = binding.bottomSheet
        val behaviour = BottomSheetBehavior.from(sheet)
        behaviour.state = BottomSheetBehavior.STATE_EXPANDED

        binding.bottomSheetDeleteTv.setOnClickListener {

        }

        return binding.root
    }
}