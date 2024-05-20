package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemLikeSongBinding

class LikeFeedRVAdapter(private var songList : List<Song>,
                        private var flag : ArrayList<Boolean>,
                        private var toggle_visible : Boolean,
                        private var all_toggle_status : Boolean)
    : RecyclerView.Adapter<LikeFeedRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onClick(song : Song, isChecked : Boolean)
    }
    private lateinit var mItemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListner : MyItemClickListener){
        mItemClickListener = itemClickListner
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): LikeFeedRVAdapter.ViewHolder {
        val binding : ItemLikeSongBinding = ItemLikeSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikeFeedRVAdapter.ViewHolder, position: Int) {
        holder.bind(songList[position])
        if(toggle_visible){
            holder.binding.likeSongBtnIv.visibility = ImageView.VISIBLE
        } else{
            holder.binding.likeSongBtnIv.visibility = ImageView.GONE
        }
        if(all_toggle_status){
            holder.binding.likeSongBtnIv.setImageResource(R.drawable.like_song_clicked_btn)
        } else{
            holder.binding.likeSongBtnIv.setImageResource(R.drawable.like_song_unclicked_btn)
        }


        holder.itemView.setOnClickListener {
            if(flag[position]){
                holder.binding.likeSongBtnIv.setImageResource(R.drawable.like_song_unclicked_btn)
                flag[position] = false
            } else{
                holder.binding.likeSongBtnIv.setImageResource(R.drawable.like_song_clicked_btn)
                flag[position] = true
            }
            mItemClickListener.onClick(songList[position], flag[position])
        }
    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding : ItemLikeSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song : Song){
            binding.likeSongAlbumCoverIv.setImageResource(song.coverImg)
            binding.likeSongTitleTv.text = song.title
            binding.likeSongSingerTv.text = song.singer
            binding.likeSongAlbumNameTv.text = song.album

            // 전체 선택 클릭/해제(allStatus 상태 따라)
            //Log.d("status", "전체 선택 클릭 해제 : $allStatus")
            //binding.likeSongToggleBtnTb.isChecked = allStatus
        }
    }

    // 삭제 버튼 클릭 시 아이템들 삭제 후 데이터 재정의
    fun setItems(list : ArrayList<Song>){
        songList = list.toList()
        notifyDataSetChanged()
    }
    fun setFlag(list : ArrayList<Boolean>){
        flag = list
        notifyDataSetChanged()
    }
    fun setToggleStatusInit(b : Boolean){
        all_toggle_status = b
        notifyDataSetChanged()
    }
    fun setToggleVisibleInit(b : Boolean){
        toggle_visible = b
        notifyDataSetChanged()
    }
}