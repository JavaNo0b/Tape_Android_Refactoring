package com.janob.tape_aos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    lateinit var binding : FragmentFollowingBinding
    lateinit var userDatas : List<User>

    lateinit var followRVAdapter : FollowRVAdapter

    // 팔로잉 리스트 대로 userDatas 재설정 변수
    private var following_list = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)

        // ** userDatas 초기화 **
        var change_following_list = ArrayList<User>()
        for(i in 0 until following_list.size){
            var change_user = TapeDatabase.Instance(context as MainActivity).userDao().getUserByName(following_list[i])
            change_following_list.add(change_user)
        }
        userDatas = change_following_list.toList()

        // ** adapter 변수 선언 **
        followRVAdapter = FollowRVAdapter(userDatas)

        // ** Recycler Adapter : search_user_rv **
        binding.followingRv.adapter = followRVAdapter
        binding.followingRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        followRVAdapter.setMyItemClickListener(object : FollowRVAdapter.MyItemClickListener{
            override fun onItemClick(user : User) {
                // 클릭시 타인 개인 프로필 페이지 프래그먼트로 전환 + 데이터 전달(gson)
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fm, OtherprofileFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val userJson = gson.toJson(user)
                            putString("user", userJson)
                        }
                    })
                    .commitAllowingStateLoss()

            }

        })

        return binding.root
    }

    // 팔로잉 리스트 대로 userDatas 재설정 함수
    fun setFollowingListStatus(list: ArrayList<String>){
        this.following_list = list
    }
}