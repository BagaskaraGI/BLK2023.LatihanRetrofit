package com.example.latihanretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumService::class.java)

        GlobalScope.launch(Dispatchers.Main){
            val response = api.getAlbums()
            if(response.isSuccessful){
                var data = ""
                for (album in response.body()!!){
                    data += album.toString()
                    Log.d(TAG, album.toString())
                }
                val textView = findViewById<TextView>(R.id.tvData)
                textView.text = data
            }
        }
    }
}