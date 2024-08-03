package com.example.agecalcolator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }


    }


    fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        // Viene utilizzata la lambda expression
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                // Codice da eseguire quando l'utente seleziona una data
                Toast.makeText(this, "Year was $selectedYear, month was ${selectedMonth + 1}, day was $selectedDayOfMonth", Toast.LENGTH_LONG).show()


                // Setta l'ui
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate


                // Converte la data nel formato giusto. Comprensibile per manipolarlo siccome è una stringa
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                val theDate = sdf.parse(selectedDate)
                Log.i("theDate", "$theDate");

                // Converte la data del formato cambiato in minuti
                val selectedDateInMinute = theDate.time / 60000
                Log.i("selectedDateInMinute", "$selectedDateInMinute");


                // Prendo la data corrente
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                Log.i("currentDate", "$currentDate");

                // La convertisco in minuti
                val currentDateInMinutes = currentDate.time / 60000
                Log.i("currentDateInMinutes", "$currentDateInMinutes");

                // La i minuti in finale è la differenza tra i minuti della data corrente - la data selezionata.
                val differenceInMinute = currentDateInMinutes - selectedDateInMinute
                Log.i("differenceInMinute", "$differenceInMinute");

                tvAgeInMinutes?.text = differenceInMinute.toString()
            },

            year,
            month,
            day
        ).show()



    }


}