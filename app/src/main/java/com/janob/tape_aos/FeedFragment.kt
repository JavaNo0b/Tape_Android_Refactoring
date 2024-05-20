package com.janob.tape_aos

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    lateinit var binding : FragmentFeedBinding

    private var tape_list = ArrayList<Tape>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        // Recycler Adapter : feed_content_rv
        var feedRVAdapter = FeedRVAdapter(tape_list.toList())
        binding.feedContentRv.adapter = feedRVAdapter
        binding.feedContentRv.layoutManager = GridLayoutManager(context, 3)

        // GridLayout 간격 맞추기
        binding.feedContentRv.run {
            adapter = FeedRVAdapter(tape_list.toList())
            addItemDecoration(GridSpacingItemDecoration(3, 20))
        }


        return binding.root
    }

    // GridLayout 간격 맞추는 class
    internal class GridSpacingItemDecoration(private val spanCount : Int, private val spacing : Int) : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val position : Int = parent.getChildLayoutPosition(view)
            val column = position % spanCount + 1

            if(position < spanCount){
                outRect.top = spacing
            }
            if(column == spanCount){
                outRect.right = spacing
            }
            outRect.left = spacing
            outRect.right = spacing
            outRect.top = spacing
            outRect.bottom = spacing
        }
    }

    // ** 테이프 세팅 **
    fun setTapeList(list : ArrayList<Tape>){
        this.tape_list = list
    }
}