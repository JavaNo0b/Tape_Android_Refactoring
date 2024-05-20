package com.janob.tape_aos

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    lateinit var feedFragment : FeedFragment
    lateinit var likeFeedFragment : LikeFeedFragment

    private var tape_list = ArrayList<Tape>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        feedFragment = FeedFragment()
        likeFeedFragment = LikeFeedFragment()

        // ** 테이프 세팅 **
        feedFragment.setTapeList(tape_list)

        return when(position){
            0 -> feedFragment
            else -> likeFeedFragment
        }
    }

    fun setTapeList(list : ArrayList<Tape>){
        this.tape_list = list
    }
}