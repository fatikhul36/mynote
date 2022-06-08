package com.example.mynote.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class movie (
    @PrimaryKey(autoGenerate = true)
    val id: Int ,
    val title: String ,
    val desc: String
)