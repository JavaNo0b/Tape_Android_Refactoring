package com.janob.tape_aos
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.janob.tape_aos.databinding.FragmentPostIntroductionBinding


class PostIntroductionFragment : Fragment() {
    private lateinit var imageUri : Uri
    private lateinit var imageView : ImageView
    private lateinit var imageIcView : ImageView
    private lateinit var titleTv : EditText
    private lateinit var contentTv : EditText
    private lateinit var alertTv : TextView
    private lateinit var btnContinue : ImageView

    //뷰모델
    private val introViewModel: IntroductionViewModel by lazy {
        ViewModelProvider(this).get(IntroductionViewModel::class.java)
    }
    interface PostIntroductionListener { fun onPostIntroductionCompleted( uri:Uri,title:String,content:String)}
    lateinit var listener : PostIntroductionListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is PostIntroductionListener){
            listener = parentFragment as PostIntroductionListener
        }
        else{
            throw Exception("인터페이스 구현하세요")
        }
    }
    val callBack : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val intent = it.data
                intent?.data?.let{
                    imageUri = it
                    imageView.setImageURI(imageUri)
                    //뷰모델에 저장
                    introViewModel.imageUri?.value = imageUri
                    imageIcView.visibility = View.INVISIBLE

                }
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostIntroductionBinding.inflate(inflater)
        //이미지뷰 참조
        imageView = binding.backgroundPhoto
        imageIcView = binding.icPhoto
        titleTv = binding.titleTapeEt
        contentTv = binding.contentTapeEt
        alertTv = binding.alertTv
        btnContinue = binding.btnPostContinue
        binding.backgroundPhoto.setOnClickListener {
            //사진앱 데이터 베이스 접근 요청
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            callBack.launch(intent)
        }
        titleTv.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                //뷰모델에 저장
                introViewModel.setIntroTitle(s.toString())
                //초기화
                btnContinue.setOnClickListener{
                    //empty
                }
                if(titleTv.text.toString().isNullOrBlank()){
                    btnContinue.setImageResource(R.drawable.btn_continue_inactive)
                    alertTv.visibility = View.VISIBLE
                }
                else if(titleTv.text.length >= 60)
                {
                    btnContinue.setImageResource(R.drawable.btn_continue_inactive)
                    alertTv.visibility = View.VISIBLE
                }
                else if(contentTv.text.length >= 280){
                    btnContinue.setImageResource(R.drawable.btn_continue_inactive)
                    alertTv.visibility = View.VISIBLE
                }
                else{
                    btnContinue.setImageResource(R.drawable.btn_continue_active)
                    alertTv.visibility = View.INVISIBLE

                    btnContinue.setOnClickListener{
                        listener.onPostIntroductionCompleted(imageUri,"title","content")
                    }
                }

            }
            override fun afterTextChanged(s: Editable?) {
            }

        })

        contentTv.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                introViewModel.setIntroContent(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        return binding.root
    }
}