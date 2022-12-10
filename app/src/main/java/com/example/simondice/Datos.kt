package com.example.simondice

import androidx.room.*

@Entity
data class Datos(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo(name = "puntos") val puntos: Int?
)