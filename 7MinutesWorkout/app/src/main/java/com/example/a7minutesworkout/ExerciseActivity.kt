package com.example.a7minutesworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress: Int = 0

    private var exerciseTimer: CountDownTimer? = null
    private var eserciseProgress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        // Setta l'action bar siccome inizialmente nel themes disabilitato
        setSupportActionBar(binding?.toolbarExercise)


        // Serve per abilitare la freccia indietro sull'action bar
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }

    private fun setupRestView(){
        if(restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        binding?.flProgressBar?.visibility = View.GONE

        // Molto importante, permette di cambiare il constraint dinamicamente.
        val layoutParams = binding?.tvTitle?.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.bottomToTop = binding?.flExerciseView!!.id
        binding?.tvTitle?.text = "Exercise Name"
        binding?.flExerciseView?.visibility = View.VISIBLE
        if(exerciseTimer!= null){
            exerciseTimer?.cancel()
            eserciseProgress = 0
        }
        setExerciseProgressBar()
    }


    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        // Voglio che ogni 1000 millisecondi esegue il Metodo della durata totale di 10000 millisecondi
        restTimer = object: CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                restProgress++;
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Exercises will start now!",Toast.LENGTH_LONG).show()
                setupExerciseView()
            }

        }.start()

    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = eserciseProgress

        // Voglio che ogni 1000 millisecondi esegue il Metodo della durata totale di 10000 millisecondi
        exerciseTimer = object: CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                eserciseProgress++;
                binding?.progressBarExercise?.progress = 30 - eserciseProgress
                binding?.tvTimerExercise?.text = (30 - eserciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Exercises is done!",Toast.LENGTH_LONG).show()
            }

        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer!= null){
            exerciseTimer?.cancel()
            eserciseProgress = 0
        }
        binding = null
    }
}