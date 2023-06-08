package com.example.latihanretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


const val BASE_URL = "https://jsonplaceholder.typicode.com"
class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.rvListAlbum)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumService::class.java)

        GlobalScope.launch(Dispatchers.Main){
            val response = api.getAlbums()
            if(response.isSuccessful){
                // Assuming you have retrieved the albumList from the API response
                val albumList: List<AlbumsItem> = response.body()?.toList() ?: emptyList()

                // Create and set the adapter
                val adapter = AlbumListAdapter(albumList)
                recyclerView.adapter = adapter

                // Set the layout manager
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

//                var data = ""
//                for (album in response.body()!!){
//                    data += album.toString()
//                    Log.d(TAG, album.toString())
//                }
//                val textView = findViewById<TextView>(R.id.tvData)
//                textView.text = data

            }


        }
    }
}