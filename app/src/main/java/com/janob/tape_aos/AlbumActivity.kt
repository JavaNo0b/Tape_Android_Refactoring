package com.janob.tape_aos

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityAlbumBinding
import java.lang.Math.abs
import kotlin.properties.Delegates

class AlbumActivity : AppCompatActivity() {

    lateinit var binding: ActivityAlbumBinding
    var albumId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        albumId = intent.getLongExtra("albumId", 0).toInt()
        Log.d("position2", albumId.toString()) //0
        initAlbumFragment(albumId)

    }


    private fun initAlbumFragment(albumId : Int){
        var albumFragment = AlbumFragment()
        var bundle = Bundle()
        bundle.putInt("albumId",albumId)
        albumFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.album_fm, albumFragment)
            .commitAllowingStateLoss()
    }
}