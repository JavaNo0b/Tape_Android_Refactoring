package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityProfile1Binding

class Profile1Activity : AppCompatActivity() {

    lateinit var binding : ActivityProfile1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfile1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val Intent = intent
        val userid = Intent.getLongExtra("UserID", 0)

        //todo : 빈공간 누르면 키보드 hide
        binding.profile1NicknameEt.setOnFocusChangeListener { view, hasFocus -> }


        binding.profile1ButtonBtn.setOnClickListener{
            if(checkProfile()){

                //val loginuserDB = TapeDatabase.Instance(this).loginuserDao()!!
                val Nickname = binding.profile1NicknameEt.text.toString()
                val Intent = intent
                val Userid = Intent.getLongExtra("userid", 0)

                Log.d("Login1111", Nickname)
                Log.d("Login1111", Userid.toString())
                //val User : LoginUser? = loginuserDB.getLoginUser(Userid)

                /*User?.let {
                    User.nickname = Nickname
                    Log.d("Login1111", User.nickname!!)
                    loginuserDB.updateUser(User)
                }
*/
                //Log.d("Login1111", loginuserDB.getLoginUsers().toString())

                val intent = Intent(this, Profile2Activity::class.java)
                intent.putExtra("userid", Userid)
                intent.putExtra("usernickname", Nickname)
                startActivity(intent)
                finish()


            }
        }
    }


    fun checkProfile(): Boolean {    //프로필조건 충족하는지 확인

        Log.d("Profile1", "첫번째 오류임")
        if (binding.profile1NicknameEt.text.isEmpty() || binding.profile1NicknameEt.text.toString().length > 20) {
            Log.d("Profile1", "첫번째 오류")
            binding.profile1NicknameError2Tv.visibility = View.VISIBLE
            binding.profile1NicknameError1Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.GONE
            Log.d("Profile1", "첫번째 오류")
            return false

        } else if (!profile1NicknameEtCheck(binding.profile1NicknameEt.text.toString())) {
            binding.profile1NicknameError1Tv.visibility = View.VISIBLE
            binding.profile1NicknameError2Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.GONE
            Log.d("Profile1", "두번째 오류")
            return false
        }
        else if(CheckExistNickname(binding.profile1NicknameEt.text.toString())) {//다른 아이디와 같을때
            binding.profile1NicknameError1Tv.visibility = View.GONE
            binding.profile1NicknameError2Tv.visibility = View.GONE
            binding.profile1NicknameError3Tv.visibility = View.VISIBLE
            Log.d("Profile1", "세번째 오류")
            return false
        }
        Log.d("Profile1", "성공")
        return true
    }


    private fun profile1NicknameEtCheck(string : String) :Boolean{  //영어, 숫자, 마침표, _ 외 다른 거 있는지 확인
        val check = "[a-zA-Z0-9._]+".toRegex()
        return string.matches(check)
    }


    private fun CheckExistNickname(string : String) :Boolean {  //이미 있는 닉네임인지 확인

        val Nicknamedb = TapeDatabase.Instance(this).loginuserDao()!!
        val Nickname : LoginUser? = Nicknamedb.getLoginUserNickname(string)

        // 입력된 닉네임과 동일한 닉네임이 이미 존재하는지 확인
        val existNickname : Boolean = string.equals(Nickname?.nickname)
        return existNickname
    }

}