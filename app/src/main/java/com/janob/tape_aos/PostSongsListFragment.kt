package com.janob.tape_aos
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.janob.tape_aos.databinding.FragmentPostSongsListBinding

class PostSongsListFragment : Fragment() {


    //뷰 모델
    private val apiResponseViewModel : ApiResponseViewModel by lazy{
        ViewModelProvider(this).get(ApiResponseViewModel::class.java)
    }
    private val songListViewModel : SongListViewModel by lazy {
        ViewModelProvider(this).get(SongListViewModel::class.java)
    }
    private val includedSongListViewModel : IncludedSongListViewModel by lazy{
        ViewModelProvider(this).get(IncludedSongListViewModel::class.java)
    }
    lateinit var manager :LinearLayoutManager
    lateinit var adapter : SongAdapter
    lateinit var recyclerView : RecyclerView
    lateinit var searchView : SearchView
    interface SongsListListener { fun onSongsListCompleted() }
    lateinit var listener: SongsListListener
    lateinit var binding : FragmentPostSongsListBinding
    lateinit var songCount :TextView
    lateinit var continueBtn : ImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(parentFragment is PostSongsListFragment.SongsListListener){
            listener = parentFragment as SongsListListener
        }
        else{
            throw Exception("인터페이스 미구현")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostSongsListBinding.inflate(inflater, container,false)
        recyclerView = binding.searchSongRecyclerView
        manager = LinearLayoutManager(context)
        adapter = SongAdapter(emptyList())
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        //searchView
        searchView = binding.searchView
        searchView.apply{
            setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                //글자 제출할 때마다
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d("PostSongsListFragment","query text submit $query")
                    apiResponseViewModel.fetchSearchTerm(query!!)
                    return true
                }
                //글자 바뀔 때마다
                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("PostSongsListFragment","query text change $newText")
                    return false
                }

            })
        }

        //song count
        songCount = binding.includedSongCountTv
        continueBtn = binding.btnPostContinue
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //api 연동
//        apiResponseViewModel.responseLiveData.observe(
//            viewLifecycleOwner,
//            Observer {
//               melonResponse ->Log.d(TAG,"got response $melonResponse")
//                //리사이클러뷰 adapter 갱신
//                recyclerView.adapter = SongAdapter(melonResponse)
//           }
//        )
        songListViewModel.songListLiveData.observe(
            viewLifecycleOwner,
            Observer{
                    songs -> Log.d(TAG,"got songs ${songs.size}")
                updateUI(songs)
            }
        )

        songListViewModel.songsCount.observe(
            viewLifecycleOwner,
            Observer{
                    songsCount->Log.d(TAG,"now songs count $songsCount")
                songCount.text = "$songsCount 곡을 선택하셨습니다"
                //다음 버튼
                if(songsCount > 0){
                    continueBtn.setImageResource(R.drawable.btn_continue_active)
                    continueBtn.setOnClickListener{
                        listener.onSongsListCompleted()
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

    }

    fun updateUI(songs : List<Song>){
        //통째로 리스트 교체
        adapter = SongAdapter(songs)
        recyclerView.adapter = adapter
    }


    inner class SongAdapter(var songs :List<Song>) : ListAdapter<Song, SongItemViewHolder>(SongItemCallback()){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
            val view = layoutInflater.inflate(R.layout.item_song,parent,false)
            return SongItemViewHolder(view)
        }
        override fun getItemCount(): Int {
            return songs.size
        }
        override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
            return holder.bind(songs[position])
        }
    }
    inner class SongItemViewHolder(view : View) : ViewHolder(view){

        private lateinit var song : Song
        private var songId :Long? = null
        private lateinit var includedSong : IncludedSong

        private var added = false
        private val songTitle = view.findViewById<TextView>(R.id.song_title_tv)
        private val songSinger = view.findViewById<TextView>(R.id.song_singer_tv)
        private val songCover = view.findViewById<ImageView>(R.id.song_cover_img)
        private val songAlbumName = view.findViewById<TextView>(R.id.song_album_title)
        fun bind(song : Song){
            this.song = song
            songTitle.text = song.title
            songSinger.text = song.singer
            songCover.setImageResource(song.coverImg)
            songAlbumName.text = song.album

            includedSong = IncludedSong(song.album, song.title, song.singer, song.coverImg, 10)

            itemView.setOnClickListener {

                if(!added){
                    added = true
                    //클릭 이벤트
                    songId = includedSongListViewModel.includedSongRepository.add(includedSong)
                    Log.d("post song list fragment","now add song $songId")
                    songListViewModel.plusSong()
                    itemView.findViewById<ImageView>(R.id.btn_add_to_tape).setImageResource(R.drawable.btn_added_to_tape)
                    itemView.findViewById<ImageView>(R.id.btn_already_checked).visibility = View.VISIBLE
                }
                else{
                    added = false
                    includedSongListViewModel.includedSongRepository.deleteById(songId)
                    Log.d("post song list fragment","now delete song $songId")
                    songListViewModel.minusSong()
                    itemView.findViewById<ImageView>(R.id.btn_add_to_tape).setImageResource(R.drawable.btn_add_to_tape)
                    itemView.findViewById<ImageView>(R.id.btn_already_checked).visibility = View.INVISIBLE
                }


            }
        }
    }
}