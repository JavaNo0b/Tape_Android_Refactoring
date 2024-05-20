package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.janob.tape_aos.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding : FragmentProfileBinding
    private val info = arrayListOf("게시글", "좋아요 한 곡")

    private val settingFragment = SettingFragment()
    private val followFragment = FollowFragment()
    lateinit var profileVPAdapter : ProfileVPAdapter

    lateinit var my_user : User
    lateinit var my_tape_list : ArrayList<Tape>

    var jwtStatus : Boolean = true

    // 데이터 받기위한 변수
    private val gson : Gson = Gson()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        // init
        getJwt()
        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
        setInit(my_user)
        my_tape_list = ArrayList(my_user.tapeList)

        // ** tabLayout과 viewPager2 연결 **
        profileVPAdapter = ProfileVPAdapter(this)
        binding.profileContentVp.adapter = profileVPAdapter
        TabLayoutMediator(binding.profileContentTb, binding.profileContentVp){
                tab, position -> tab.text = info[position]
        }.attach()

        // 테이프 세팅
        profileVPAdapter.setTapeList(my_tape_list)

        // 팔로워, 팔로잉 text 클릭 리스너
        followTextClick()

        // 프로필 수정 버튼 클릭 -> 프로필 수정 activity로 전환
        binding.profileProfileEditBtn.setOnClickListener {
            val intent = Intent(activity, ProfileEditActivity::class.java)
            startActivity(intent)
        }

        // 메뉴
        binding.profileMenuBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, settingFragment)
                .commitAllowingStateLoss()
        }


        // 임시
        binding.profilePostTv.setOnClickListener {
            val intent = Intent(activity, ProfilePostActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        my_user = TapeDatabase.Instance(context as MainActivity).userDao().getMyUser(1)
        Log.d("eunseo", "프로필 이름 = " + my_user.name)
        setInit(my_user)
    }


    // 처음 회원가입/로그인하고 내 프로필 정보 설정
    private fun setInit(user : User){
        binding.profileProfileIv.setImageBitmap(user.userImg!!)
        binding.profileNameTv.text = user.name
        binding.profileCommentTv.text = user.comment

        binding.profileTapeNumTv.text = user.tapeList.size.toString()
        binding.profileFollowerNumTv.text = user.followerList.size.toString()
        binding.profileFollowingNumTv.text = user.followingList.size.toString()

        // TODO: 좋아요 노래 설정 구현 이어서 진행
    }

    private fun getJwt() {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val db = TapeDatabase.Instance(context as MainActivity)

        val userId = spf!!.getString("jwt", "")!!.toLong()
        val loginUser : LoginUser? = db.loginuserDao().getLoginUser(userId)

        // db init
        db.userDao().updateUserImgByUserKey(loginUser!!.profileimg, 1)
        db.userDao().updateUserNameByUserKey(loginUser.nickname, 1)
        db.userDao().updateUserCommentByUserKey(loginUser.profileintro, 1)
    }

    private fun followTextClick(){
        binding.profileFollowerLl.setOnClickListener {
            val status : String = "follower"

            // 뒤로가기x
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, followFragment.apply {
                    arguments = Bundle().apply {
                        putString("status", status)

                        val gson = Gson()
                        val userJson = gson.toJson(my_user)
                        putString("pass_user", userJson)
                    }
                })
                .commitAllowingStateLoss()
        }
        binding.profileFollowingLl.setOnClickListener {
            val status : String = "following"

            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, followFragment.apply {
                    arguments = Bundle().apply {
                        putString("status", status)

                        val gson = Gson()
                        val userJson = gson.toJson(my_user)
                        putString("pass_user", userJson)
                    }
                })
                .commitAllowingStateLoss()
        }
    }
}