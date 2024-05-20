package com.janob.tape_aos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemSearchuserBinding

class FollowRVAdapter(private var userList : List<User>) : RecyclerView.Adapter<FollowRVAdapter.ViewHolder>() {
    interface MyItemClickListener{
        fun onItemClick(user : User)
    }

    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowRVAdapter.ViewHolder {
        val binding : ItemSearchuserBinding = ItemSearchuserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowRVAdapter.ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(userList[position])
        }
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(val binding : ItemSearchuserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : User){
            if(user.userImg != null){
                binding.itemSearchuserUserimgIv.setImageBitmap(user.userImg!!)
            }
            binding.itemSearchuserNameTv.text = user.name
            binding.itemSearchuserCommentTv.text = user.comment
        }
    }

    // 검색 기능을 위한 추가 함수
    fun setItems(list : ArrayList<User>){
        userList = list
        notifyDataSetChanged()
    }
}