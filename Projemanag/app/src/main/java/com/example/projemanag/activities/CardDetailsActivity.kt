package com.example.projemanag.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projemanag.R
import com.example.projemanag.databinding.ActivityCardDetailsBinding
import com.example.projemanag.databinding.ActivityMembersBinding

class CardDetailsActivity : AppCompatActivity() {

    private var binding: ActivityCardDetailsBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }
}