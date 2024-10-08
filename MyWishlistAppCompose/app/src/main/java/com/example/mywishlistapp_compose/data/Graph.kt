package com.example.mywishlistapp_compose.data

import android.content.Context
import androidx.room.Room
import com.example.mywishlistapp_compose.repository.WishRepository


// Piccolo esempio di Dipendent Injection.
// Object è solo una classe esistente per l'intera applicazione (Singleton.)

object Graph {
    lateinit var database: WishDatabase
    // by lazy la variabile vene inizializzata (delegata (by)) solo una volta al primo accesso alla repository
    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }


    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }
}