package com.example.mynote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mynote.room.Constant
import com.example.mynote.room.movie
import com.example.mynote.room.movieDb
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy { movieDb(this) }
    private var movieid: Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getmovie()
            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getmovie()
            }
        }
    }

    fun setupListener() {
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                db.moviedao().addmovie(
                    movie(0,AddMovieTitle.text.toString(),AddMovieDesc.text.toString())
                )

                finish()
            }

        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                db.moviedao().updatemovie(
                    movie(movieid,AddMovieTitle.text.toString(),AddMovieDesc.text.toString())
                )

                finish()
            }

        }
    }

    fun getmovie(){
        movieid = intent.getIntExtra("intent_id",0)
        CoroutineScope(Dispatchers.IO).launch{
            var movie = db.moviedao().getmovie( movieid) [0]
            AddMovieTitle.setText( movie.title)
            AddMovieDesc.setText( movie.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}