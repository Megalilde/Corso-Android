package com.example.mywishlistapp_compose

import android.app.Application
import com.example.mywishlistapp_compose.data.Graph

class WishListApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}