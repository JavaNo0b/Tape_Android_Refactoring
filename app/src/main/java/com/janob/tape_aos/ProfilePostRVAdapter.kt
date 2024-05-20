package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemTapeBinding

class ProfilePostRVAdapter(private val tapeList : List<Tape>) : RecyclerView.Adapter<ProfilePostRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(tape : Tape, clickStatus : Float)
    }
    private lateinit var mItemClickListener : MyItemClickListener
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ProfilePostRVAdapter.ViewHolder {
        val binding : ItemTapeBinding = ItemTapeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfilePostRVAdapter.ViewHolder, position: Int) {
        holder.bind(tapeList[position])
        holder.itemView.alpha = 0.5F

        holder.itemView.setOnClickListener {
            if(holder.itemView.alpha == 0.5F){
                holder.itemView.alpha = 1F
            } else{
                holder.itemView.alpha = 0.5F
            }

            val checkStatus : Float = holder.itemView.alpha
            mItemClickListener.onItemClick(tapeList[position], checkStatus)
        }
    }

    override fun getItemCount(): Int = tapeList.size

    inner class ViewHolder(val binding : ItemTapeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tape : Tape){
            binding.itemTapeTapetitleTv.text = tape.tapeTitle
            binding.itemTapeSingerTv.text = tape.singer
            binding.itemTapeUsernameTv.text = tape.userName
            binding.itemTapeAlbumcoverImgIv.setImageResource(tape.albumCover!!)
            binding.itemTapeUserimageIv.setImageResource(tape.userImage!!)
        }
    }
}