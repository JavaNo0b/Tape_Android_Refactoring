package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemFeedBinding

class FeedRVAdapter(var tapeDatas : List<Tape>) : RecyclerView.Adapter<FeedRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FeedRVAdapter.ViewHolder {
        val binding : ItemFeedBinding = ItemFeedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedRVAdapter.ViewHolder, position: Int) {
        holder.bind(tapeDatas[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick()
        }
    }

    override fun getItemCount(): Int = tapeDatas.size

    inner class ViewHolder(val binding : ItemFeedBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tape : Tape){
            // 여기 설정을 다시해야 함... -> 테이프들 array를 만들어서 -> 테이프의 썸네일 이미지를 set하게 해야 함
            // 일단 임시로 프로필 사진 사용
            binding.itemFeedAlbumcoverImgIv.setImageResource(tape.albumCover!!)
        }
    }

}