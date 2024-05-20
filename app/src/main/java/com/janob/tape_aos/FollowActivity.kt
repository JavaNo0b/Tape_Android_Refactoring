package com.janob.tape_aos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityFollowBinding

class FollowActivity : AppCompatActivity() {

    lateinit var binding : ActivityFollowBinding
    private val information = arrayListOf("팔로워", "팔로잉")

    lateinit var userDatas : List<User>

    private var search_list = ArrayList<User>()
    private var original_list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RoomDB 데이터 받기
        userDatas = TapeDatabase.Instance(this).userDao().getAll()

        // tabLayout과 viewPager2 연결
        // activity ver
        /*
        val followAdpater = FollowVPAdapter(this)
        binding.followerContentVp.adapter = followAdpater
        TabLayoutMediator(binding.followerContentTb, binding.followerContentVp){
                tab, position -> tab.text = information[position]
        }.attach()
        */

        // tabLayout 초기 포커스 설정
        val status = intent.getStringExtra("status")
        if(status == "following"){
            binding.followerContentTb.selectTab(binding.followerContentTb.getTabAt(1))
        }

        // 뒤로가기
        binding.followerBackIv.setOnClickListener {
            finish()
        }

        //
        //
        // ** search **
        //
        //
        // adapter 변수 선언
        val searchRVAapter = SearchRVAdapter(userDatas)

        original_list = ArrayList(userDatas)

        // editText 리스너 작성
        val editText = binding.followerEdittextEt

        //
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // X
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // X
            }

            override fun afterTextChanged(s: Editable?) {
                var searchText : String = editText.text.toString()
                search_list.clear()

                if(searchText == ""){
                    searchRVAapter.setItems(original_list)
                }
                else{
                    for(i in 0..original_list.size - 1){
                        if(original_list[i].name!!.toLowerCase().contains(searchText.toLowerCase())){
                            search_list.add(original_list[i])
                        }
                        searchRVAapter.setItems(search_list)
                    }
                }
            }

        })
    }
}