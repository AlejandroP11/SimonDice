package com.example.simondice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

class DAO {
    @Dao
    interface UserDao {
        @Query("SELECT * FROM Datos")
        fun getAll(): List<Datos>

        @Query("SELECT * FROM Datos WHERE puntos = SELECT MAX(puntos) FROM Datos")

        @Insert
        fun insertAll(Dato: Datos)

        @Delete
        fun delete(Dato: Datos)
    }
}