package com.janob.tape_aos

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ActivityReplyBinding


class ReplyActivity : AppCompatActivity()
{
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var recyclerView : RecyclerView
    lateinit var binding: ActivityReplyBinding
    lateinit var tapeReplyData: MutableList<Reply>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.replyBackIv.setOnClickListener { finish() }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        //roomDB에서 데이터 가져오기
        tapeReplyData = TapeDatabase.Instance(this).replyDao().getAll()

        //리사이클러뷰에 데이터 연결
        val manager = LinearLayoutManager(this)

        val adapter = ReplyAdapter(tapeReplyData, this, object : ReplyAdapter.MyItemClickListner {
            override fun onEditClick(reply: Reply) {
                Log.d("click", "editClick2")
                val intent = Intent(this@ReplyActivity, ReplyModifyActivity::class.java)
                intent.putExtra("reply", reply)
                resultLauncher.launch(intent)
            }

            override fun onDeleteClick(reply: Reply) {
                //roomDB 업데이트
                TapeDatabase.Instance(this@ReplyActivity).replyDao().delete(reply)
                //recyclerView 업데이트
                tapeReplyData.remove(reply)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })
//        val adapter = ReplyAdapter(tapeReplyData,this)
//
//        adapter.setMyItemClickLitner(object: ReplyAdapter.MyItemClickListner {
//            override fun onEditClick(reply: Reply) {
//                Log.d("click","editClick2")
//                val intent = Intent(this@ReplyActivity,ReplyModifyActivity::class.java)
//                intent.putExtra("reply",reply)
//                resultLauncher.launch(intent)
//            }
//
//            override fun onDeleteClick(reply: Reply) {
//                //roomDB 업데이트
//                TapeDatabase.Instance(this@ReplyActivity).replyDao().delete(reply)
//                //recyclerView 업데이트
//                tapeReplyData.remove(reply)
//                val adapter = ReplyAdapter(tapeReplyData,this@ReplyActivity)
//                recyclerView.adapter = adapter
//                //아이템이 추가되고 UI가 바뀐걸 업데이트해주는코드
//                recyclerView.adapter?.notifyDataSetChanged()
//            }
//        })

        setResultNext()

        recyclerView = binding.replyRecyclerView
        recyclerView.setHasFixedSize(false)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        binding.replyBtnAddReply.setOnClickListener{
            addReply()
        }
    }

    fun addReply() {
        val reply: String? = binding.replyEditTextEt.text.toString()
        val lastItem = tapeReplyData.last()
        TapeDatabase.Instance(this).replyDao().insert(Reply(lastItem.idx+1, reply,
            lastItem.id?.plus(1)
        ))
        binding.replyEditTextEt.text = null
        tapeReplyData.add(Reply(lastItem.idx+1, reply,
            lastItem.id?.plus(1)
        ))

        val adapter = ReplyAdapter(tapeReplyData, this, object : ReplyAdapter.MyItemClickListner {
            override fun onEditClick(reply: Reply) {
                Log.d("click", "editClick2")
                val intent = Intent(this@ReplyActivity, ReplyModifyActivity::class.java)
                intent.putExtra("reply", reply)
                resultLauncher.launch(intent)
            }

            override fun onDeleteClick(reply: Reply) {
                //roomDB 업데이트
                TapeDatabase.Instance(this@ReplyActivity).replyDao().delete(reply)
                //recyclerView 업데이트
                tapeReplyData.remove(reply)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })
        recyclerView.adapter = adapter

        //아이템이 추가되고 UI가 바뀐걸 업데이트해주는코드
        recyclerView.adapter?.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setResultNext(){
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            // 서브 액티비티로부터 돌아올 때의 결과 값을 받아 올 수 있는 구문
            if (result.resultCode == RESULT_OK){

                val replyResult = result.data?.getParcelableExtra("reply", Reply::class.java)!!
                replyResult.let {
                    val updatedIdx = it.idx
                    val updatedText = it.text

                    // Room 데이터베이스 업데이트
                    TapeDatabase.Instance(this).replyDao().updateReply(updatedText, updatedIdx.toLong())

                    // 리사이클러뷰 업데이트
                    tapeReplyData.find { reply -> reply.idx == updatedIdx }?.apply {
                        text = updatedText
                        recyclerView.adapter?.notifyItemChanged(updatedIdx)
                    }
                }
            }
        }
    }
}