package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemTapeBinding

class RealtimeRVAdapter(private val tapeList : List<Tape>) : RecyclerView.Adapter<RealtimeRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RealtimeRVAdapter.ViewHolder {
        val binding : ItemTapeBinding = ItemTapeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RealtimeRVAdapter.ViewHolder, position: Int) {
        holder.bind(tapeList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick()
        }
    }

    override fun getItemCount(): Int = tapeList.size

    inner class ViewHolder(val binding: ItemTapeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tape : Tape){
            binding.itemTapeTapetitleTv.text = tape.tapeTitle
            binding.itemTapeSingerTv.text = tape.singer
            binding.itemTapeUsernameTv.text = tape.userName
            binding.itemTapeAlbumcoverImgIv.setImageResource(tape.albumCover!!)
            binding.itemTapeUserimageIv.setImageResource(tape.userImage!!)
        }
    }
}