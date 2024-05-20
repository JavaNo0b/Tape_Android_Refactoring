package com.janob.tape_aos

import androidx.recyclerview.widget.DiffUtil

class SongItemCallback : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }

}