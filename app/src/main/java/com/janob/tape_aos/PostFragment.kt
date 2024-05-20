package com.janob.tape_aos
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.janob.tape_aos.databinding.FragmentPostBinding
const val TAG = "TAPE_SONG_LIST_FRAGMENT"
class PostFragment : Fragment(),
    PostSelectTypeFragment.SelectTypeListener,
    PostIntroductionFragment.PostIntroductionListener,
    PostSongsListFragment.SongsListListener,
    PostIncludedSongsFragment.IncludedSongsListener,
    PostBackHomeFragment.PostBackToHomeListener{

    private  var imageUri:Uri? = null
    private  var title:String? = null
    private  var content:String? = null


    lateinit var binding : FragmentPostBinding
    private var type :Int =  TYPE_NONE
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(layoutInflater)
        //프래그먼트 교체
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostSelectTypeFragment())
            .addToBackStack(null)
            .commit()
        return binding.root
    }
    override fun onSelectTypeCompleted(type: Int) {
        if(type == TYPE_SINGLE){
            this.type = TYPE_SINGLE
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostSongsListFragment())
                .addToBackStack(null)
                .commit()
        }
        else if(type == TYPE_ALBUM){
            this.type = TYPE_ALBUM
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostIntroductionFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onPostIntroductionCompleted(uri: Uri, title: String, content: String) {

        this.imageUri = uri
        this.title = title
        this.content = content

        Log.d("PostFragment","imageUri($imageUri) title($title) content($content)")
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostSongsListFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onSongsListCompleted() {

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostIncludedSongsFragment())
            .addToBackStack(null)
            .commit()

    }

    override fun onIncludedSongsCompleted() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PostBackHomeFragment())
            .addToBackStack(null)
            .commit()
    }
    override fun onPostAllCompleted() {
        var intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }



}