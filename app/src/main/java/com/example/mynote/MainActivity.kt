package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynote.room.Constant
import com.example.mynote.room.movie
import com.example.mynote.room.movieDb
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var movieAdapter : MovieAdapter

    val db by lazy { movieDb(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setuplistener()
        setupRecycleview()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch{
            val movies = db.moviedao().getmovies()
            Log.d("MainActivity","dbresponse: $movies")
            withContext(Dispatchers.Main){
                movieAdapter.setData(movies)
            }
    }}

    fun setuplistener(){
        addmovie.setOnClickListener {
            intentId(0,Constant.TYPE_CREATE)
        }
    }

    fun intentId(movieid: Int,intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id",movieid)
                .putExtra("intent_type",intentType)
        )
    }

    private fun setupRecycleview(){
        movieAdapter = MovieAdapter(arrayListOf(),object : MovieAdapter.OnAdapterListener{
            override fun onClick(movie: movie) {
                    intentId(movie.id,Constant.TYPE_READ)
            }

            override fun onUpdate(movie: movie) {
                    intentId(movie.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(movie: movie) {
                CoroutineScope(Dispatchers.IO).launch{
                    db.moviedao().deletemovie( movie )
                    loadData()
                }
            }

        })
        rv_movie.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = movieAdapter
        }
    }
}