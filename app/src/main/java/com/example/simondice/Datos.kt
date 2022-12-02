package com.example.simondice

import androidx.room.*

@Entity
data class Datos(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "puntos") val puntos: Int?,
    @ColumnInfo(name = "fecha") val fecha: String?
)