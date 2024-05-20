package com.janob.tape_aos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.janob.tape_aos.databinding.ActivityProfilePostBinding

class ProfilePostActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfilePostBinding
    lateinit var profilePostRVAdapter : ProfilePostRVAdapter
    lateinit var tapeList : List<Tape>
    lateinit var my_user : User

    lateinit var my_user_tapeList : ArrayList<Tape>
    private var addTapeListCount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfilePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        my_user = TapeDatabase.Instance(this).userDao().getMyUser(1)
        my_user_tapeList = ArrayList(my_user.tapeList)
        tapeList = TapeDatabase.Instance(this).tapeDao().getAll()
        profilePostRVAdapter = ProfilePostRVAdapter(tapeList)


        // recycler
        binding.profilePostRecyclerRv.adapter = profilePostRVAdapter
        binding.profilePostRecyclerRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 종료
        binding.profilePostBackBtnIv.setOnClickListener {
            finish()
        }


        // 아이템 클릭
        itemClick()
        // 추가하기 버튼 클릭
        postingBtnClick()

    }
    private fun itemClick(){
        profilePostRVAdapter.setMyItemClickListener(object : ProfilePostRVAdapter.MyItemClickListener{
            override fun onItemClick(tape: Tape, checkStatus: Float) {
                // 1. 테이프 정보 user.tapeList에 저장
                if(checkStatus == 1F){
                    my_user_tapeList.add(tape)
                    addTapeListCount++
                    setBtnBackground()

                } else{
                    my_user_tapeList.remove(tape)
                    addTapeListCount--
                    setBtnBackground()
                }
            }
        })
    }

    private fun postingBtnClick(){
        binding.profilePostPostingBtn.setOnClickListener {
            my_user.tapeList = this.my_user_tapeList.toList()
            TapeDatabase.Instance(this).userDao().updateTapeListByUserKey(my_user_tapeList.toList(), 1)

            if(addTapeListCount != 0){
                finish()
            }
        }
    }

    private fun setBtnBackground(){
        if(addTapeListCount == 0){
            binding.profilePostPostingBtn.setBackgroundResource(R.drawable.btn_continue_inactive)
        } else{
            binding.profilePostPostingBtn.setBackgroundResource(R.drawable.btn_continue_active)
        }
    }
}