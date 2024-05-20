package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.FragmentLikeFeedBinding

class LikeFeedFragment : Fragment() {

    lateinit var binding : FragmentLikeFeedBinding
    lateinit var likeFeedRVAdapter : LikeFeedRVAdapter
    lateinit var likeList : List<Song>
    lateinit var my_user : User

    lateinit var change_likeList : ArrayList<Song>
    private var flag = ArrayList<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikeFeedBinding.inflate(inflater, container, false)

        // init
        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
        likeList = my_user.likeList
        change_likeList = ArrayList(my_user.likeList)
        flagInit(false)

        // adapter 변수 선언
        likeFeedRVAdapter = LikeFeedRVAdapter(likeList, flag, false, false)

        // ** Recycler Adapter : like_feed_rv **
        binding.likeFeedRv.adapter = likeFeedRVAdapter
        binding.likeFeedRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)



        // 편집 toggleButton 설정
        editBtnClick()

        // 전체선택 toggleButton 설정
        allToggleClick()

        // 삭제 클릭 리스너
        deleteBtnClick()


        return binding.root
    }

    private fun flagInit(b : Boolean){
        flag.clear()
        for(i in 0 until change_likeList.size){
            flag.add(b)
        }
    }
    private fun editBtnClick(){
        binding.likeFeedEditToggleTb.setOnCheckedChangeListener{ CompoundButton, b->
            if(b){
                // 전체선택 toggle 나타나게 하기
                binding.likeFeedAllSelectToggleTb.visibility = ToggleButton.VISIBLE
                binding.likeFeedAllSelectTv.visibility = TextView.VISIBLE

                // 삭제 버튼 나타나게 하기
                binding.likeFeedDeleteButtonBtn.visibility = Button.VISIBLE

                //
                likeFeedRVAdapter.setToggleVisibleInit(true)

                //
                likeFeedRVAdapter.setMyItemClickListener(object : LikeFeedRVAdapter.MyItemClickListener{
                    override fun onClick(song: Song, isChecked: Boolean) {
                        if(isChecked){
                            change_likeList.remove(song)
                        } else{
                            change_likeList.add(song)
                        }
                    }
                })
            } else{
                // 전체선택 toggle 사라지게 하기
                binding.likeFeedAllSelectToggleTb.visibility = ToggleButton.INVISIBLE
                binding.likeFeedAllSelectTv.visibility = TextView.INVISIBLE

                // 삭제 버튼 사라지게 하기
                binding.likeFeedDeleteButtonBtn.visibility = Button.GONE

                //
                likeFeedRVAdapter.setToggleVisibleInit(false)

                //
                //like_songList.clear()
                likeFeedRVAdapter.setToggleStatusInit(false)
            }
        }
    }
    private fun allToggleClick(){
        binding.likeFeedAllSelectToggleTb.setOnCheckedChangeListener{ CompoundButton, b->
            if(b){
                // 전체선택 클릭!
                binding.likeFeedAllSelectToggleTb.setBackgroundResource(R.drawable.like_song_clicked_btn)

                //
                likeFeedRVAdapter.setToggleStatusInit(true)

                //
                change_likeList.clear()

            } else{
                // 전체선택 취소ㅠㅠ
                binding.likeFeedAllSelectToggleTb.setBackgroundResource(R.drawable.like_song_unclicked_btn)

                //
                likeFeedRVAdapter.setToggleStatusInit(false)

                //
                change_likeList = ArrayList(likeList)
            }
        }
    }
    private fun deleteBtnClick(){
        binding.likeFeedDeleteButtonBtn.setOnClickListener {
            likeFeedRVAdapter.setItems(change_likeList)
            likeFeedRVAdapter.setToggleStatusInit(false)
            likeList = change_likeList.toList()
            flagInit(false)

            my_user.likeList = likeList
            TapeDatabase.Instance(context as MainActivity).userDao().updateLikeListByUserKey(likeList, 1)
        }
    }
}