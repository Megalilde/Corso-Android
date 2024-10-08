package com.example.mywishlistapp_compose.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Wish::class], version = 1, exportSchema = false)
abstract class WishDatabase: RoomDatabase() {

    // Ci da l'accesso a tutti i metodi di WishDao.
    abstract fun wishDao(): WishDao
}