package com.janob.tape_aos

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KaKaoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Kakao Sdk 초기화
        KakaoSdk.init(this, "acf9ccdb9befb1509a4e53ad023da96b")
    }
}