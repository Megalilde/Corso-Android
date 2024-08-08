package com.example.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase:RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    // Una dichiarazione di oggetto all'interno di una classe può essere contrassegnata con la companion
    companion object {

        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase{

            // Kotlin può essere SmartCast, ovvero compiare un valore di una instanza corrente in una variabile locale
            synchronized(this){
                var instance = INSTANCE

                // Cancella de ricostruisce il database (di norma non si fa)
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}