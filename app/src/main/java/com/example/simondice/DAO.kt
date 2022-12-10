package com.example.simondice

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT puntos FROM Datos WHERE id = '1'")
    suspend fun getMaxPunt(): Int

    @Query("INSERT INTO Datos (puntos) VALUES (0)")
    suspend fun insertPunt()

    @Update
    suspend fun update(datos: Datos)
}