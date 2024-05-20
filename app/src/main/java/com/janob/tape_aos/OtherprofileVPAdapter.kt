package com.janob.tape_aos

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OtherprofileVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    lateinit var feedFragment : FeedFragment

    private var tape_list = ArrayList<Tape>()
    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        feedFragment = FeedFragment()

        // ** 테이프 세팅 **
        feedFragment.setTapeList(tape_list)

        return when(position){
            else -> feedFragment
        }
    }

    fun setTapeList(list : ArrayList<Tape>){
        this.tape_list = list
    }
}