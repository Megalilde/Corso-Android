package com.example.viewbindingrv

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewbindingrv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 1 Crea il dato Task e la lista dei task
    // 2 Crea ui per RecyclerView e la recyclerview_item che si riferisce al singolo elemento della lista
    // 3 Successivamente crea l'adapter
    // 4 Prendi l'adapter e assegna l'adapter alla RecyclerView dell'activity main
    var binding: ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // 4
        val adapter = MainAdapter(TaskList.taskList)
        //binding?.taskRv?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding?.taskRv?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}