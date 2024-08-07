package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [EmployeeEntity::class], version = 1)
abstract class EmployeeDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    // Una dichiarazione di oggetto all'interno di una classe può essere contrassegnata con la companion
    companion object {

        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getInstance(context: Context): EmployeeDatabase{

            // Kotlin può essere SmartCast, ovvero compiare un valore di una instanza corrente in una variabile locale
            synchronized(this){
                var instance = INSTANCE

                // Cancella de ricostruisce il database (di norma non si fa)
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}