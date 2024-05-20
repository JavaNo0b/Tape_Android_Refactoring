package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignIn.setOnClickListener{

            onClick(binding.loginSignIn)
        }

    }

    protected fun onClick(view : View){
        when (view?.id) {
            view.id -> {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                        Log.d("Login1111", "확인2")
                        Log.d("test", "확인3")
                        if (error != null) {
                            Log.d("login failure(onClick)", "카카오톡으로 로그인 실패 $error")
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            } else {
                                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                            }
                        } else if (token != null) {
                            Log.d("login success(onClick)", "카카오톡으로 로그인 성공 ${token.accessToken}")
                            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                            firstlogincheck()
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                }
            }
        }
    }

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.d("login failure(mCallback)", "카카오 계정으로 로그인 실패 $error")
        } else if (token != null) {
            Log.d("login success(mCallback)", "카카오 계정으로 로그인 성공 ${token.accessToken}")
            Log.d("Login1111", "확인4")
            firstlogincheck()
        }
    }


    private fun firstlogincheck(){

        Log.d("Login1111", "확인5")
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("Login1111", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("Login1111", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                NextActivity(user.id)
            }
        }
    }

    fun NextActivity(userid: Long?){

        val loginuserDB = TapeDatabase.Instance(this).loginuserDao()!!
        val existUser : LoginUser? = loginuserDB.getLoginUser(userid)

        if(existUser != null){  //이미 계정이 존재함
            saveJwt(userid.toString())
            Log.d("Login1111", loginuserDB.getLoginUsers().toString())
            Log.d("Login1111", userid.toString())
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userid", userid)
            startActivity(intent)
            finish()
        }
        else{
            //val basic = LoginUser(userid, null, null, null)
            //loginuserDB.insert(basic)
            Log.d("Login1111", "user{$userid}")
            Log.d("Login1111", loginuserDB.getLoginUsers().toString())

            val intent = Intent(this, OnboardActivity::class.java)
            intent.putExtra("userid", userid)
            startActivity(intent)
            finish()
        }
    }


    private fun saveJwt(jwt: String) {
        val spf = getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf.edit()
        Log.d("Login1111", jwt)
        // 키 값 : "jwt", 인자값 : jwt
        editor.putString("jwt", jwt)
        editor.apply()
    }
}