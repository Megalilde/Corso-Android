package com.example.a7minutesworkout

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flStartButton : FrameLayout = findViewById(R.id.flStart)

        flStartButton.setOnClickListener{
            Toast.makeText(this, "Bottone Cliccato!", Toast.LENGTH_LONG).show()
        }
    }
}