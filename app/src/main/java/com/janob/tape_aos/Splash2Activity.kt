package com.janob.tape_aos

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.util.Utility
import com.janob.tape_aos.databinding.ActivitySplash2Binding


class Splash2Activity : AppCompatActivity() {
    lateinit var binding : ActivitySplash2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Splash1)

        binding = ActivitySplash2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
        }, 3000)

    }
}