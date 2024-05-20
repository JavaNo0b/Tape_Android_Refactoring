package com.janob.tape_aos
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.FragmentTapeBinding


class TapeFragment : Fragment() {

    lateinit var tapeAlbumRVAdapter:TapeAlbumRVAdapter
    lateinit var tapeAlbumRV :RecyclerView
    var isWatched = false
//    private val tapeListViewModel :TapeListViewModel by lazy{
//        ViewModelProvider(this).get(TapeListViewModel::class.java)
//    }
    lateinit var binding: FragmentTapeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTapeBinding.inflate(layoutInflater)
        tapeAlbumRV = binding.tapeTapelistRv

        //db 데이터 가져오기
        var tapeAlbumData = TapeDatabase.Instance(requireContext()).tapeDao().getAll()
        //리사이클러뷰 어댑터
        tapeAlbumRVAdapter = TapeAlbumRVAdapter(tapeAlbumData, requireContext())
        tapeAlbumRV.adapter=tapeAlbumRVAdapter
        tapeAlbumRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        tapeAlbumRVAdapter.setMyItemClickLitner(object: TapeAlbumRVAdapter.MyItemClickListner {

            override fun onItemClick(album: Tape) {
                changeAlbumActivity(album)

            }
        })

        binding.button.setOnClickListener { buttonClick() }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun updateUI(tapes :List<Tape>){
        tapeAlbumRVAdapter = TapeAlbumRVAdapter(tapes,requireContext())
        tapeAlbumRV.adapter = tapeAlbumRVAdapter
    }

    private fun changeAlbumActivity(album: Tape){
        val intent = Intent(activity,AlbumActivity::class.java)
        intent.apply {
            this.putExtra("albumId",album.id) // 데이터 넣기
        }
        Log.d("position1", album.id.toString()) //album.id
        startActivity(intent)
    }


    //게시물이 있을 때, 없을 때를 임의로 확인하기 위한 버튼
    private fun buttonClick(){
        if(binding.tapeTapeLayout.visibility == View.VISIBLE){
            binding.tapeTapeLayout.visibility = View.GONE
            binding.tapeTapelistRv.visibility = View.GONE

            binding.tapeMaketapePlusLayout.visibility = View.VISIBLE
            binding.tapeTapelistZero.visibility = View.VISIBLE
        }else{
            binding.tapeTapeLayout.visibility = View.VISIBLE
            binding.tapeTapelistRv.visibility = View.VISIBLE

            binding.tapeMaketapePlusLayout.visibility = View.GONE
            binding.tapeTapelistZero.visibility = View.GONE
        }
    }
}