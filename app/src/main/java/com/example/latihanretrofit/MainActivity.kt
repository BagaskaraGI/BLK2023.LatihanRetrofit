package com.example.latihanretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
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
        val adapter = AlbumListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumService::class.java)

        // Launch the coroutine to fetch albums
        fetchAlbums(adapter, api)

//        GlobalScope.launch(Dispatchers.Main){
//            val response = api.getAlbums()
//            if(response.isSuccessful){
//                // Assuming you have retrieved the albumList from the API response
//                val albumList: List<AlbumsItem> = response.body()?.toList() ?: emptyList()
//
//                // Create and set the adapter
//                val adapter = AlbumListAdapter(albumList)
//                recyclerView.adapter = adapter
//
//                // Set the layout manager
//                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
//
////                var data = ""
////                for (album in response.body()!!){
////                    data += album.toString()
////                    Log.d(TAG, album.toString())
////                }
////                val textView = findViewById<TextView>(R.id.tvData)
////                textView.text = data
//
//            }
//        }

    }



    private fun fetchAlbums(adapter : AlbumListAdapter, api : AlbumService){
        lifecycleScope.launch {
            try {
                val albums: Albums = getAlbums(api)
                adapter.setData(albums)
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private suspend fun getAlbums(api : AlbumService): Albums {

        val response: Response<Albums> = api.getAlbums()

        // Check if the API call was successful
        if (response.isSuccessful) {
            val album: Albums? = response.body()

            // Convert the retrieved album to Albums type
            return album?.let { Albums().apply { addAll(it) } } ?: Albums()
        } else {
            // Handle the API error
            throw Exception("API call failed with code: ${response.code()}")
        }
    }

}