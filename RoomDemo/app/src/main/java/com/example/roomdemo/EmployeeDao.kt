package com.example.roomdemo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employeeEntity: EmployeeEntity)

    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)


    // Flow viene utilizzato per mantenere il valore che può essere cambiato a runtime

    @Query("SELECT * FROM `employee-table` ")
    fun fetchAllEmployees():Flow<List<EmployeeEntity>>


    // Flow viene utilizzato per mantenere il valore che può essere cambiato a runtime

    @Query("SELECT * FROM `employee-table` where id = :id ")
    fun fetchEmployeeById(id: Int):Flow<EmployeeEntity>


}