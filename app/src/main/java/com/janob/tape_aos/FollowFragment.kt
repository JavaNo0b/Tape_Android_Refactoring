package com.janob.tape_aos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentFollowBinding

class FollowFragment() : Fragment() {

    lateinit var binding : FragmentFollowBinding
    private val information = arrayListOf("팔로워", "팔로잉")

    lateinit var userDatas : List<User>

    // 검색용
    private var search_list = ArrayList<User>()
    private var original_list = ArrayList<User>()

    //
    lateinit var followAdapter : FollowVPAdapter

    lateinit var follower_list : ArrayList<String>
    lateinit var following_list : ArrayList<String>

    // 데이터 받기위한 변수
    private val gson : Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)

        // ** RoomDB 데이터 받기 **
        userDatas = TapeDatabase.Instance(context as MainActivity).userDao().getAll()
        // ** OtherprofileFragment, ProfileFragment 에서 받아옴 **
        val userJson = arguments?.getString("pass_user")
        val user = gson.fromJson(userJson, User::class.java)
        // ** 팔로워, 팔로잉 리스트 초기화 **
        follower_list = ArrayList(user.followerList)
        following_list = ArrayList(user.followingList)


        // ** tabLayout과 viewPager2 연결 **
        followAdapter = FollowVPAdapter(this)
        binding.followContentVp.adapter = followAdapter
        TabLayoutMediator(binding.followContentTb, binding.followContentVp){
                tab, position -> tab.text = information[position]
        }.attach()

        // ** 팔로워, 팔로잉 리스트 대로 userDatas 재설정 : -> FollowerVPAdapter로 follower_list, following_list 전달 **
        followAdapter.setFollowerList(follower_list)
        followAdapter.setFollowingList(following_list)

        // ** tabLayout 초기 포커스 설정 **
        // OtherprofileFragment에서 팔로워, 팔로우 status 데이터 받아오기
        val status = arguments?.getString("status")
        if(status == "follower"){
            binding.followContentTb.selectTab(binding.followContentTb.getTabAt(0))
        }
        else if(status == "following"){
            binding.followContentTb.selectTab(binding.followContentTb.getTabAt(1))
        }

        // ** 뒤로가기 **
        binding.followBackIv.setOnClickListener {
            // 나중에 구현
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
        val editText = binding.followEdittextEt

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

        return binding.root
    }
}