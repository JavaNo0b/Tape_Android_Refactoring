package com.janob.tape_aos

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.janob.tape_aos.databinding.ActivityReplyModifyBinding

class ReplyModifyActivity  : AppCompatActivity(){
    lateinit var reply : Reply
    lateinit var binding: ActivityReplyModifyBinding


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReplyModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reply = intent.getParcelableExtra("reply", Reply::class.java)!!

        val editText = binding.replyModifyEdittext
        val editText2 = binding.replyModifyTextEt
        editText.text = Editable.Factory.getInstance().newEditable(reply.text)
        editText2.text = editText.text

        binding.replyModifyCompleteTv.setOnClickListener {
            intent.putExtra("reply", Reply(reply.idx, editText.text.toString()))
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.replyModifyBackIv.setOnClickListener {
            finish()
        }

        editText.setOnClickListener {
            editText.clearFocus() // EditText1의 포커스 해제
            editText2.requestFocus() // EditText2로 포커스 이동
        }

        // 첫 번째 EditText에 대한 TextWatcher
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 전에 수행할 작업
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때마다 수행할 작업
                if (editText2.text.toString() != charSequence.toString()) {
                    editText2.text = Editable.Factory.getInstance().newEditable(charSequence)
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                // 입력 후에 수행할 작업
            }
        })

        // 두 번째 EditText에 대한 TextWatcher
        editText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 전에 수행할 작업
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때마다 수행할 작업
                if (editText.text.toString() != charSequence.toString()) {
                    editText.text = Editable.Factory.getInstance().newEditable(charSequence)
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                // 입력 후에 수행할 작업
            }
        })
    }
}