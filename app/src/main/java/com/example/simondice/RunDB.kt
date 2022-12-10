package com.example.simondice

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Datos::class],
    version = 1
)

abstract class RunDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}
