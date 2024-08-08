package com.example.roomdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Esso permette di interaggire con la tabella del database chiamata EmployeeEntity
        // Quindi per eseguire le query questo è obbligatorio.
        val employeeDao = (application as EmployeeApp).db.employeeDao()

        deleteAll(employeeDao)
        binding?.btnAdd?.setOnClickListener {
            addRecord(employeeDao)
        }

        lifecycleScope.launch {

            // Using collect perchè al metodo della fetch utilizziamo flow
            // Nota employeeList perche è una lista di EmployeeDao
            employeeDao.fetchAllEmployee().collect{ employeeList ->

                setupListOfDataIntoRecyclerView(employeeList,employeeDao)
            }
        }

    }


    private fun deleteAll(employeeDao: EmployeeDao) {
        lifecycleScope.launch {
            employeeDao.deleteAll()
            Toast.makeText(applicationContext, "All records deleted", Toast.LENGTH_LONG).show()
        }
    }

    fun addRecord(employeeDao: EmployeeDao){
        val name = binding?.etName?.text.toString()
        val email = binding?.etEmailId?.text.toString()

        if(email.isNotEmpty() && name.isNotEmpty()){
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name=name, email = email))
                Toast.makeText(applicationContext, "Record saved",  Toast.LENGTH_LONG).show()
                binding?.etName?.text?.clear()
                binding?.etEmailId?.text?.clear()
            }
        }else{
            Toast.makeText(applicationContext, "Name or Email cannot be blank",  Toast.LENGTH_LONG).show()
        }
    }

    private fun setupListOfDataIntoRecyclerView(employeesList: List<EmployeeEntity>, employeeDao: EmployeeDao){
        if(employeesList.isNotEmpty()){
            val itemAdapter = ItemAdapter(employeesList,


                )
            binding?.rvItemsList?.layoutManager = LinearLayoutManager(this)
            binding?.rvItemsList?.adapter = itemAdapter
            binding?.rvItemsList?.visibility = View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility = View.GONE
        }else{
            binding?.rvItemsList?.visibility = View.GONE
            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE
        }
    }
}