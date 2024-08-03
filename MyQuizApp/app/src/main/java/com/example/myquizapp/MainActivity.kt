package com.example.myquizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnStart : Button = findViewById(R.id.btn_start)
        val etName : EditText = findViewById(R.id.etName)
        btnStart.setOnClickListener {
            // Utilizza 'it' per accedere alla vista cliccata
            if(etName.text.isEmpty()){
                Toast.makeText(this, "Please enter your name:", Toast.LENGTH_SHORT).show()
            }else{
                //Intent passa come parametro activity attuale è l'activity di destinazione
                //Per eseguirlo startActivity.Esegui finish() per finire l'attivita.
                val intent = Intent(this,QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                startActivity(intent)
                finish()
            }
            //Questo è di esempio per capire le lamba expression
        /*btnStart.setOnClickListener { v: View ->
            if(etName.text.isEmpty()){
                Toast.makeText(this, "Please enter your name:", Toast.LENGTH_SHORT).show()
            }else{
                //Intent passa come parametro activity attuale è l'activity di destinazione
                //Per eseguirlo startActivity.Esegui finish() per finire l'attivita.
                val intent = Intent(this,QuizQuestionsActivity::class.java)
                startActivity(intent)

                finish()
            }
        }*/

        }

    }
}