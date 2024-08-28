package com.example.projemanag.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projemanag.R
import com.example.projemanag.databinding.ActivityMembersBinding
import com.example.projemanag.models.Board
import com.example.projemanag.utils.Constants

class MembersActivity : AppCompatActivity() {


    private var binding: ActivityMembersBinding? = null

    private lateinit var mBoardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembersBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if(intent.hasExtra(Constants.BOARD_DETAIL)){
            mBoardDetails = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
        }
        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.toolbarMembersActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.members)
        }

        binding?.toolbarMembersActivity?.setNavigationOnClickListener { onBackPressed() }
    }
}