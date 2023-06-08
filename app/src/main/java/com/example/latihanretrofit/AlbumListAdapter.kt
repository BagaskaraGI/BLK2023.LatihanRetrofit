package com.example.latihanretrofit


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlbumListAdapter(private val albumList: List<AlbumsItem>) : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {
//    private var albumList = emptyList<Albums>()

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val idTextView: TextView = itemView.findViewById(R.id.tvId)
        val idUserTextView: TextView = itemView.findViewById(R.id.tvUserId)
        val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val curentAlbum = albumList[position]
        holder.idTextView.text = curentAlbum.id.toString()
        holder.idUserTextView.text = curentAlbum.userId.toString()
        holder.titleTextView.text = curentAlbum.title
    }




}