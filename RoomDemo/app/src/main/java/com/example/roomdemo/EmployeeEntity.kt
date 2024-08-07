package com.example.roomdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


// Definita nome della tabella
@Entity(tableName = "employee-table")
data class EmployeeEntity(

    // Chiave primaria.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",

    // Sto cambiando il nome della colonna
    @ColumnInfo(name = "email-id")
    val email: String = "",




)
