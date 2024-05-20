package com.janob.tape_aos

import androidx.recyclerview.widget.DiffUtil

class IncludedSongItemCallback: DiffUtil.ItemCallback<IncludedSong>(){
    override fun areItemsTheSame(oldItem: IncludedSong, newItem: IncludedSong): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: IncludedSong, newItem: IncludedSong): Boolean {
        return oldItem == newItem
    }

}