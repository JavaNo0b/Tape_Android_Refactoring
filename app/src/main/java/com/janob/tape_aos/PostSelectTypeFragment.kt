package com.janob.tape_aos

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostSelectTypeBinding

const val TYPE_NONE = 0
const val TYPE_SINGLE = 1
const val TYPE_ALBUM = 2
class PostSelectTypeFragment : Fragment() {


    interface SelectTypeListener { fun onSelectTypeCompleted(type : Int) }
    lateinit var listener : SelectTypeListener
    lateinit var binding : FragmentPostSelectTypeBinding

    private var type = 0 //

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //부모 프래그먼트와 연결
        if(parentFragment is PostSelectTypeFragment.SelectTypeListener){
            listener = parentFragment as SelectTypeListener
        }else{
            throw Exception("인터페이스 미구현")
        }
    }

    private fun updateUI(){
        //토글
        if(type == TYPE_SINGLE ){
            binding.backgroundSingleTape.setImageResource(R.drawable.tape_type_active)
            binding.backgroundAlbumTape.setImageResource(R.drawable.tape_type_inactive)
            binding.textSingleTape.setTextColor(Color.parseColor("#ffffff"))
            binding.textDescSingleTape.setTextColor(Color.parseColor("#ffffff"))
            binding.textAlbumTape.setTextColor((Color.parseColor("#000000")))
            binding.textDescAlbumTape.setTextColor(Color.parseColor("#000000"))
            binding.background.setImageResource(R.drawable.eclipse_72_active)
            binding.background2.setImageResource(R.drawable.eclipse_72_inactive)

            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_active)
        }
        else if(type == TYPE_ALBUM){
            binding.backgroundAlbumTape.setImageResource(R.drawable.tape_type_active)
            binding.backgroundSingleTape.setImageResource(R.drawable.tape_type_inactive)

            binding.textAlbumTape.setTextColor(Color.parseColor("#ffffff"))
            binding.textDescAlbumTape.setTextColor(Color.parseColor("#ffffff"))

            binding.textSingleTape.setTextColor(Color.parseColor("#000000"))
            binding.textDescSingleTape.setTextColor(Color.parseColor("#000000"))
            binding.background2.setImageResource(R.drawable.eclipse_72_active)
            binding.background.setImageResource(R.drawable.eclipse_72_inactive)


            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_active)
        }
        else{
            binding.backgroundSingleTape.setImageResource(R.drawable.tape_type_inactive)
            binding.backgroundAlbumTape.setImageResource(R.drawable.tape_type_inactive)

            binding.btnPostContinue.setImageResource(R.drawable.btn_continue_inactive)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostSelectTypeBinding.inflate(inflater,container,false)


        //버튼 활성화
        binding.backgroundSingleTape.setOnClickListener{
            type = TYPE_SINGLE
            updateUI()
        }
        binding.backgroundAlbumTape.setOnClickListener {
            type = TYPE_ALBUM
            updateUI()
        }

        binding.btnPostContinue.setOnClickListener {
            if(type != TYPE_NONE) {
                //다음페이지로..
                listener.onSelectTypeCompleted(type)
            }
        }
        return binding.root
    }
}