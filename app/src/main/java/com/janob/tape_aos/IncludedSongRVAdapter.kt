package com.janob.tape_aos

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemSongcoverBinding

class IncludedSongRVAdapter(private val songsList: List<IncludedSong>,private val context: Context): RecyclerView.Adapter<IncludedSongRVAdapter.ViewHolder>() {
    var songDB = TapeDatabase.Instance(context)
    interface MyItemClickListner{ //item clicklistner를 저장하기 위한 인터페이스
//        fun onLikeClick(song: IncludedSong)
    }

    private lateinit var mItemClickListner: MyItemClickListner //아래 받은 것을 내부에서 사용하기 위해 선언
    fun setMyItemClickLitner(itemClickListner: MyItemClickListner) { //외부에서의 itemClickListner를 받기 위한 함수
        mItemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder { //뷰 홀더 생성 함수
        val binding: ItemSongcoverBinding = ItemSongcoverBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //재활용하기 위해 item객체를 viewholder에게 던짐
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //position값을 가지고 있으므로 보통 클릭이벤트는 여기서 작성한다.

        holder.bind(songsList[position]) //binding해주는 함수
//        holder.binding.itemSongcoverCoverimgIv.setOnClickListener {mItemClickListner.onPlayClick(songsList[position]) }
        holder.binding.itemSongcoverLikeBtn.setOnClickListener {
            holder.setLike(songsList[position])
        }
    }

    override fun getItemCount(): Int = songsList.size //songsList의 크기 반환

    inner class ViewHolder(val binding: ItemSongcoverBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(song: IncludedSong){
            binding.itemSongcoverTitleTv.text = song.title
            binding.itemSongcoverSingerTv.text = song.singer
            binding.itemSongcoverCoverimgIv.setImageResource(song.img!!)
            if(song.isLiked){
                binding.itemSongcoverLikeBtn.setImageResource(R.drawable.btn_like)
            }else{
                binding.itemSongcoverLikeBtn.setImageResource(R.drawable.btn_unlike)
            }

        }
        fun setLike(song: IncludedSong){
            Log.d("click","Click")
            if(song.isLiked){
                Log.d("click","likeClick")
                binding.itemSongcoverLikeBtn.setImageResource(R.drawable.btn_unlike)
                songDB.IncludedSongDao().updateIsLikeById(isLiked = false, id = song.id)
                song.isLiked = !song.isLiked
                Toast.makeText(context,"좋아요를 취소하였습니다.${song.isLiked}", Toast.LENGTH_SHORT).show()
            }else{
                Log.d("click","unlikeClick")
                binding.itemSongcoverLikeBtn.setImageResource(R.drawable.btn_like)
                songDB.IncludedSongDao().updateIsLikeById(isLiked = true, id = song.id)
                song.isLiked = !song.isLiked
                Toast.makeText(context,"좋아요 한 곡이 담겼습니다.${song.isLiked}, ${song.id}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}