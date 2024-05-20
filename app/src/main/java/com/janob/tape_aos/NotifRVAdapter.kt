package com.janob.tape_aos


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.janob.tape_aos.databinding.ItemNotif1Binding
import com.janob.tape_aos.databinding.ItemNotif2Binding
import com.janob.tape_aos.databinding.ItemNotif3Binding


class NotifRVAdapter(private val list : MutableList<Alarm>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface MyItemClickListener{ fun onItemClick(item : Alarm) }


    private lateinit var ItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        ItemClickListener = itemClickListener
    }

    private var Notiflist = list.size
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View?
        return when(viewType){
            Alarm.notif_1 -> {
                ViewHolder1(ItemNotif1Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }
            Alarm.notif_2 -> {
                ViewHolder2(ItemNotif2Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }
            else -> {
                ViewHolder3(ItemNotif3Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val position = list[position]
        when (position.id) {
            Alarm.notif_1 -> {
            }
            Alarm.notif_2 -> {
                //(holder as ViewHolder2).title = position.reciver_id
                val viewHolder2 = holder as ViewHolder2
                viewHolder2.title = position.reciver_id
                viewHolder2.itemView.setOnClickListener { ItemClickListener.onItemClick(position) }
            }
            Alarm.notif_3 -> {
                val viewHolder3 = holder as ViewHolder3
                viewHolder3.user = position.reciver_id
                viewHolder3.title = position.alarm_id
                viewHolder3.itemView.setOnClickListener { ItemClickListener.onItemClick(position) }
            }
        }
    }

    override fun getItemCount() : Int = Notiflist

    override fun getItemViewType(position: Int): Int {
        return list[position].id
    }

    inner class ViewHolder1(view : ItemNotif1Binding) : RecyclerView.ViewHolder(view.root){

    }
    inner class ViewHolder2(view : ItemNotif2Binding) : RecyclerView.ViewHolder(view.root){
        var title = view.itemNotif2TextTv.text
    }
    inner class ViewHolder3(view : ItemNotif3Binding) : RecyclerView.ViewHolder(view.root){
        var user = view.itemNotif3TextTv1.text
        var title = view.itemNotif3TextTv2.text
    }




}