package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tape.OnboardVPAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.janob.tape_aos.databinding.ActivityOnboardBinding

class OnboardActivity : AppCompatActivity() {
    lateinit var binding : ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Intent = intent
        val userid = Intent.getLongExtra("userid", 0)

        // tab layout와 viewpager 연결
        TabLayoutMediator(binding.onboardTabTb, binding.onboardViewpagerVp) { tab, position
            ->
            when (position) {
                0 -> "온보드1"
                else -> "온보드2"
            }
        }
        // viewpager와 custom pager adapter 연결
        binding.onboardViewpagerVp.adapter = OnboardVPAdapter(this)

        //건너뛰기 누르면 바로 profile1으로 넘어가기
        binding.onboardSkipTv.setOnClickListener {
            val intent = Intent(this, Profile1Activity::class.java)
            startActivity(intent)
            finish()
        }

        // button과 viewpager 연결하기 : 다음 뷰페이저로 이동
        binding.onboardButtonBtn.setOnClickListener {
            if(binding.onboardViewpagerVp.currentItem == 0){
                binding.onboardViewpagerVp.currentItem = binding.onboardViewpagerVp.currentItem + 1
            }else{

                val intent = Intent(this, Profile1Activity::class.java)
                intent.putExtra("userid", userid)
                Log.d("Login1111", userid.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}


