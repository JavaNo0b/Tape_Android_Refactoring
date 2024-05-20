package com.janob.tape_aos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.janob.tape_aos.databinding.FragmentAlbumBinding
import java.lang.Math.abs

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    lateinit var includedSongsData: List<IncludedSong>
    lateinit var tapeData: Tape
    lateinit var songDB: TapeDatabase
    var albumId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(layoutInflater)
        albumId = arguments?.getInt("albumId")!!
        tapeData = TapeDatabase.Instance(requireContext()).tapeDao().getTape(albumId)


        setDummyIncludedSong(albumId)


        binding.albumTapeTitleTv.text = tapeData.tapeTitle


        val includedSongRVAdapter = IncludedSongRVAdapter(includedSongsData, requireContext())
        binding.albumIncludedsongsVp.adapter = includedSongRVAdapter



        binding.albumBackBtn.setOnClickListener { startActivity(Intent(requireActivity(), MainActivity::class.java)) }
//        binding.albumIncludedsongsVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // 관리하는 페이지 수. default = 1
        binding.albumIncludedsongsVp.offscreenPageLimit = 10
        // item_view 간의 양 옆 여백을 상쇄할 값
        val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
        binding.albumIncludedsongsVp.setPageTransformer { page, position ->
            val myOffset = position * -(2 * offsetBetweenPages)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
                val scaleFactor = 0.8f.coerceAtLeast(1 - abs(position))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }

        //indicator
        TabLayoutMediator(binding.albumTapeTab, binding.albumIncludedsongsVp) { _, _ -> }.attach()


        //댓글 액티비티로 이동
        binding.albumCommentBtn.setOnClickListener {

            Toast.makeText(requireContext(),"댓글 액티비티으로 이동 ",Toast.LENGTH_SHORT).show()

            var intent = Intent(activity, ReplyActivity::class.java)
            startActivity(intent)

        }

        binding.albumTapeMoreBtn.setOnClickListener { showBottomDialog() }

        return binding.root
    }

    fun setDummyIncludedSong(albumId: Int){
        songDB = TapeDatabase.Instance(requireContext())
        includedSongsData = songDB.IncludedSongDao().getSongsInAlbum(albumId!!)
    }

    //싱글테이프일 경우 indicator 가리기
    private fun indicatorSingle(){

    }


    //AlbumFragment의 BottomSheet 띄우기
    private fun showBottomDialog() {
        val bottomsheet = AlbumBottomSheet()
        bottomsheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
        bottomsheet.show(requireActivity().supportFragmentManager, "AlbumBottomSheet")
    }

}