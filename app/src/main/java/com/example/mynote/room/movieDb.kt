package com.example.mynote.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [movie::class],
    version = 1
)

abstract class movieDb : RoomDatabase(){

    abstract fun moviedao() : MovieDao

    companion object {
        @Volatile private var instance : movieDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            movieDb::class.java,
            "movie12345.db"
        ).build()
    }
}
