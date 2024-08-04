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

    private var exerciseList: MutableList<ExerciseModel>? = null
    private var currectExercisePosition = -1

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

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }

    private fun setupRestView(){
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExercise?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE


        if(restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExercise?.text = exerciseList!![currectExercisePosition + 1].getName()
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExercise?.visibility = View.INVISIBLE

        // Molto importante, permette di cambiare il constraint dinamicamente.
        //val layoutParams = binding?.tvTitle?.layoutParams as ConstraintLayout.LayoutParams
        //layoutParams.bottomToTop = binding?.flExerciseView!!.id

        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE

        if(exerciseTimer!= null){
            exerciseTimer?.cancel()
            eserciseProgress = 0
        }
        binding?.ivImage?.setImageResource(exerciseList!![currectExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!!.get(currectExercisePosition).getName()
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

                currectExercisePosition++
                setupExerciseView()
            }

        }.start()

    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = eserciseProgress

        // Voglio che ogni 1000 millisecondi esegue il Metodo della durata totale di 30000 millisecondi
        exerciseTimer = object: CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                eserciseProgress++;
                binding?.progressBarExercise?.progress = 30 - eserciseProgress
                binding?.tvTimerExercise?.text = (30 - eserciseProgress).toString()
            }

            override fun onFinish() {
                if(currectExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity,"Congratulation!!",Toast.LENGTH_LONG).show()
                }

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