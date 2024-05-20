package com.janob.tape_aos

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemReplyBinding

class ReplyAdapter(private val replies : List<Reply>, private val context: Context, private val itemClickListener: MyItemClickListner) : RecyclerView.Adapter<ReplyAdapter.ReplyItemViewHolder>() {

    private val dataList = replies

    interface MyItemClickListner{ //item clicklistner를 저장하기 위한 인터페이스
        fun onEditClick(reply: Reply)
        fun onDeleteClick(reply: Reply)
    }

    private lateinit var mItemClickListner: MyItemClickListner //아래 받은 것을 내부에서 사용하기 위해 선언
    fun setMyItemClickLitner(itemClickListner: MyItemClickListner) { //외부에서의 itemClickListner를 받기 위한 함수
        mItemClickListner = itemClickListner
    }
    inner class ReplyItemViewHolder(val binding : ItemReplyBinding) : RecyclerView.ViewHolder(binding.root)
    {
        lateinit var reply : Reply

        //댓글 바인딩
        fun bind(reply : Reply){
            this.reply = reply

            var text = binding.replyTextTv
            text.text = reply.text

        }

        //menu popup
        fun showPopup(view: View, position:Int) {
            val inflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.custom_reply_popup_menu, null)

            val width = 163.dpToPx(context)
            val height = 61.dpToPx(context)

            val popupWindow = PopupWindow(popupView, width, height, true)
            popupWindow.showAsDropDown(view, 0, 0)

            // 팝업 내의 특정 뷰 찾기
            val popupEdit: View = popupView.findViewById(R.id.custom_reply_popup_menu_edit)
            val popupDelete: View = popupView.findViewById(R.id.custom_reply_popup_menu_delete)

            // 팝업 내의 특정 뷰에 클릭 이벤트 추가
            popupEdit.setOnClickListener {
                Log.d("click", "editClick1")
                itemClickListener.onEditClick(dataList[position])
            }
            popupDelete.setOnClickListener {
                itemClickListener.onDeleteClick(dataList[position])
            }

        }

        //menu popup 사이즈 변경
        fun Int.dpToPx(context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyItemViewHolder {
        val binding : ItemReplyBinding = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ReplyItemViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ReplyItemViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.binding.replyEditBtn.setOnClickListener {
            holder.showPopup(it, position)
        }
    }
}