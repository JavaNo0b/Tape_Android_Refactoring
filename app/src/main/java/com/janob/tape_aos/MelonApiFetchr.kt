package com.janob.tape_aos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
//Repository
class MelonApiFetchr {


    private lateinit var melonApi :MelonApi
    init{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www./")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        melonApi = retrofit.create(MelonApi::class.java)
    }

    fun fetchContents() : LiveData<List<MelonSong>> {
        val call: Call<List<MelonSong>> = melonApi.fetchContents()
        return fetchMetaData(call)
    }
    fun searchContents(query:String) : LiveData<List<MelonSong>>{
        val call:Call<List<MelonSong>> = melonApi.searchContents(query)
        return fetchMetaData(call)
    }
    fun fetchMetaData(call :Call<List<MelonSong>>):LiveData<List<MelonSong>>{
        val responseLiveData : MutableLiveData<List<MelonSong>> = MutableLiveData<List<MelonSong>>()
        //웹사이트에 응답 요청
        call.enqueue(object:Callback<List<MelonSong>>{
            override fun onResponse(
                call: Call<List<MelonSong>>,
                response: Response<List<MelonSong>>
            ) {
                Log.d("MelonApiFetchr",response.body().toString())
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<List<MelonSong>>, t: Throwable) {
                Log.d("MelonApiFetchr","failed To Response")
            }

        })
        return responseLiveData
    }


}