package com.example.a7minutesworkout

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Si utilizza la Viewbinding per accedere alla vista dell'activity (xml) evitando di eseguire le findViewById per ogni componente ui.
    // Molta piu veloce ed efficiente durante la compilazione, evita conflitti per quanto riguarda gli id presenti xml (il riferimento)
    // Si utilizza di solito questo approccio. (Google raccomanda questo approccio)

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // La root sarebbe il constraintlayout
        setContentView(binding?.root)

        // Con il binding non e piu necessario scrivere quello di sotto
        // val flStartButton : FrameLayout = findViewById(R.id.flStart)


        // Nota se binding può essere nullo, di conseguenza anche gli elementi sotto di esso possono essere nulli.
        binding?.flStart?.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
    }


    // Si fa questo per evitare problemi con il binding
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}