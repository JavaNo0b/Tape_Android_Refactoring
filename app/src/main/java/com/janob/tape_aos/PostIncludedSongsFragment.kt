package com.janob.tape_aos
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.janob.tape_aos.databinding.FragmentPostIncludedSongsBinding
import kotlin.properties.Delegates

class PostIncludedSongsFragment : Fragment() {

    //테이프 완성본 저장
    lateinit var myTape : Tape
    private var imageUri: Uri = Uri.EMPTY
    private var title:String =""
    private var content:String=""
    fun instance(uri: Uri, title:String, content:String) : PostIncludedSongsFragment{
        //정보 넘겨 받음
        this.imageUri = uri
        this.title = title
        this.content = content
        return PostIncludedSongsFragment()
    }
    //뷰 모델
    private val includedSongListViewModel : IncludedSongListViewModel by lazy{
        ViewModelProvider(this).get(IncludedSongListViewModel::class.java)
    }
    private val tapeListViewModel : TapeListViewModel by lazy{
        ViewModelProvider(this).get(TapeListViewModel::class.java)
    }
    interface IncludedSongsListener { fun onIncludedSongsCompleted() }

    lateinit var binding : FragmentPostIncludedSongsBinding
    lateinit var listener: IncludedSongsListener
    lateinit var adapter:IncludedSongAdapter
    lateinit var manager:LinearLayoutManager
    lateinit var recyclerView:RecyclerView
    lateinit var continueBtn : ImageView
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is IncludedSongsListener)
            listener = parentFragment as IncludedSongsListener
        else
            throw Exception("인터페이스 미구현")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostIncludedSongsBinding.inflate(inflater)
        recyclerView = binding.songsRecyclerView
        adapter = IncludedSongAdapter(emptyList())
        manager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager
        binding.btnPostContinue.setOnClickListener {
            listener.onIncludedSongsCompleted()
        }

        continueBtn = binding.btnPostContinue

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //todo introduction 연동
        includedSongListViewModel.includedSongLiveData.observe(
            viewLifecycleOwner,
            Observer{
                    songs ->
                Log.d("POST_INCLUDED_SONGS_FRAGMENT","now included songs ${songs.size}")
                updateUI(songs)
                if(songs.isNotEmpty()){
                    continueBtn.setImageResource(R.drawable.btn_continue_active)
                    continueBtn.setOnClickListener {
                        listener.onIncludedSongsCompleted()
                    }
                }
                else{
                    continueBtn.setImageResource(R.drawable.btn_continue_inactive)
                    continueBtn.setOnClickListener {
                        //
                    }
                }


            }


        )
        includedSongListViewModel.includedSongLiveData
    }

    private fun updateUI(includedSongs: List<IncludedSong>){
        val adapter = IncludedSongAdapter(includedSongs)
        recyclerView.adapter = adapter
    }
    inner class IncludedSongAdapter(var includedSongs: List<IncludedSong>) : ListAdapter<IncludedSong,IncludedSongItemViewHolder>(IncludedSongItemCallback()) {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): IncludedSongItemViewHolder {
            val view = layoutInflater.inflate(R.layout.item_included_song,parent,false)
            return IncludedSongItemViewHolder(view)
        }

        override fun getItemCount(): Int = includedSongs.size
        override fun onBindViewHolder(holder: IncludedSongItemViewHolder, position: Int) {
            var song = includedSongs[position]
            return holder.bind(song)
        }
    }
    inner class IncludedSongItemViewHolder(view:View) : ViewHolder(view) {
        lateinit var song : IncludedSong
        val coverImg = view.findViewById<ImageView>(R.id.song_cover_img)
        val songTitle = view.findViewById<TextView>(R.id.song_title_tv)
        val songSinger = view.findViewById<TextView>(R.id.song_singer_tv)
        val songAlbumTitle = view.findViewById<TextView>(R.id.song_album_title)
        fun bind(song:IncludedSong){
            this.song = song
            coverImg.setImageResource(song.img!!)
            songTitle.text = song.title
            songSinger.text = song.singer
            songAlbumTitle.text = song.albumTitle
            itemView.setOnClickListener {
                includedSongListViewModel.includedSongRepository.delete(song)
            }
        }

    }
    override fun onStop(){
        super.onStop()
//
//        //테이프를 db에 저장
//        myTape.tapeId = 10
//        tapeListViewModel.addTape(myTape)

    }
}