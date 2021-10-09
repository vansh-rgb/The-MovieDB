package com.strink.themoviedb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.strink.themoviedb.model.Movies

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "rating")
    val rating: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)