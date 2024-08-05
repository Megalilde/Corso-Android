package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding

import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress: Int = 0
    private var restTimerDuration: Long = 10

    private var exerciseTimer: CountDownTimer? = null
    private var eserciseProgress: Int = 0
    private var exerciseTimerDuration: Long = 30


    private var exerciseList: MutableList<ExerciseModel>? = null
    private var currectExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

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
            customDialogForBackButton()
        }

        exerciseList = Constants.defaultExerciseList()

        tts = TextToSpeech(this,this)

        setupRestView()
        setupExerciseStatusRecyclerView()

    }

    private fun customDialogForBackButton(){
        // Prendo il dialog di default
        val customDialog = Dialog(this)
        // Prendo la binding della view che ho creato
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        // Setto il dialog di defailt com il mio
        customDialog.setContentView(dialogBinding.root)
        // Se clicco fuori dalla customDialog
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btYes.setOnClickListener {
            customDialog.dismiss()
            this@ExerciseActivity.finish()
        }

        dialogBinding.btNo.setOnClickListener {
            customDialog.dismiss()  // Rimuoviamo il dialog
        }
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView(){
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }


    // Questo Metodo setta l'ui inziale prima dell'esercizio
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

        // Inserimento di un audio
        try {
            val soundURI = Uri.parse("android.resource://com.example.a7minutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        }catch(e: Exception){
            e.printStackTrace()
        }

        binding?.tvUpcomingExercise?.text = exerciseList!![currectExercisePosition + 1].getName()
        setRestProgressBar()
    }


    // Questo Metodo setta l'ui dell'esercizio
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

        speakOut(exerciseList!![currectExercisePosition].getName())

        binding?.ivImage?.setImageResource(exerciseList!![currectExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!!.get(currectExercisePosition).getName()
        setExerciseProgressBar()
    }


    // Per la progress bar iniziale
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        // Voglio che ogni 1000 millisecondi esegue il Metodo della durata totale di 10000 millisecondi
        restTimer = object: CountDownTimer(restTimerDuration * 1000,1000){
            override fun onTick(p0: Long) {
                restProgress++;
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currectExercisePosition++

                exerciseList!![currectExercisePosition].setIsSelected(true)

                // Verifica se i dati della recycleview sono cambiati
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }

        }.start()

    }

    // Per la progress bar dell'esercizio
    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = eserciseProgress

        // Voglio che ogni 1000 millisecondi esegue il Metodo della durata totale di 30000 millisecondi
        exerciseTimer = object: CountDownTimer(exerciseTimerDuration * 1000,1000){
            override fun onTick(p0: Long) {
                eserciseProgress++;
                binding?.progressBarExercise?.progress = 30 - eserciseProgress
                binding?.tvTimerExercise?.text = (30 - eserciseProgress).toString()
            }

            override fun onFinish() {

                if(currectExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currectExercisePosition].setIsSelected(false)
                    exerciseList!![currectExercisePosition].setIsCompleted(true)

                    // Verifica se i dati della recycleview sono cambiati
                    exerciseAdapter!!.notifyDataSetChanged()

                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity,"Congratulation!!",Toast.LENGTH_LONG).show()
                    // Qui andrà intent per passare alla schermata finish
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
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
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null){
            player!!.stop()
        }

        binding = null
    }

    // Per la Scelta del linguaggio
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result ==  TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","The language is not supported!")
            }
        }else{
            Log.e("TTS","Initialization Failed!")
        }
    }
    // Metodo per far parlare un bot il nome dell'esercizio passato
    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }
}