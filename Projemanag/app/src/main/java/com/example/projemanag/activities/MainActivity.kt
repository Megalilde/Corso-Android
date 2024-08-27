package com.example.projemanag.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projemanag.R
import com.example.projemanag.adapters.BoardItemsAdapter
import com.example.projemanag.databinding.ActivityMainBinding
import com.example.projemanag.firebase.FirestoreClass
import com.example.projemanag.models.Board
import com.example.projemanag.models.User
import com.example.projemanag.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object{
        const val MY_PROFILE_REQUEST_CODE: Int = 11
        const val CREATE_BOARD_REQUEST_CODE: Int = 22
    }

    private lateinit var mUserName: String

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setupActionBar()


        binding?.navView?.setNavigationItemSelectedListener(this)

        FirestoreClass().loadUserData(this,true)


        findViewById<FloatingActionButton>(R.id.fab_create_board).setOnClickListener {
            val intent = Intent(this,CreateBoardActivity::class.java)
            intent.putExtra(Constants.NAME, mUserName)
            startActivityForResult(intent, CREATE_BOARD_REQUEST_CODE)
        }
    }


    // Popola la lista con le board
    fun populateBoardsListToUI(boardsList: ArrayList<Board>){
        hideProgressDialog()
        val rvBoardList: RecyclerView = findViewById(R.id.rv_boards_list)

        if(boardsList.size > 0 ){

            rvBoardList.visibility = View.VISIBLE
            findViewById<TextView>(R.id.tv_no_boards_available).visibility = View.GONE

            rvBoardList.layoutManager = LinearLayoutManager(this)
            rvBoardList.setHasFixedSize(true)

            val adapter = BoardItemsAdapter(this,boardsList)
            rvBoardList.adapter = adapter
        }else{
            rvBoardList.visibility = View.GONE
            findViewById<TextView>(R.id.tv_no_boards_available).visibility = View.VISIBLE
        }
    }


    // Settiamo l'icona hamburger e il tap su di esso.
    private fun setupActionBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main_activity)
        setSupportActionBar(findViewById(R.id.toolbar_main_activity))
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu)
        toolbar.title = resources.getString(R.string.create_board_title)

        toolbar.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    // Serve per far scomparire e apparire il Drawer
    private fun toggleDrawer(){
        if(binding?.drawerLayout?.isDrawerOpen(GravityCompat.START) == true){
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        }else{
            binding?.drawerLayout?.openDrawer(GravityCompat.START)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if(binding?.drawerLayout?.isDrawerOpen(GravityCompat.START) == true){
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        }else{
            doubleBackToExit()
        }

    }

    fun updateNavigationUserDetails(user: User, readBoardsList: Boolean){
        mUserName = user.name
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(findViewById(R.id.nav_user_image))

        val userName: TextView = findViewById(R.id.tv_username)
        userName.text = user.name

        if (readBoardsList){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().getBoardsList(this)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == MY_PROFILE_REQUEST_CODE){
            FirestoreClass().loadUserData(this)
        } else if(resultCode == Activity.RESULT_OK && requestCode == CREATE_BOARD_REQUEST_CODE){
            FirestoreClass().getBoardsList(this)

        } else {
            Log.e("Cancelled", "Cancelled")
        }
    }


    // Implementando l'interfaccia NavigationView.OnNavigationItemSelectedListener ci permette implementare questo metodo
    // Nota: Item sono uno dei due item che abbiamo aggiunto ovvero profilo e sign out
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_my_profile ->{

                startActivityForResult(Intent(this,
                    MyProfileActivity::class.java),
                    MY_PROFILE_REQUEST_CODE)
            }
            R.id.nav_sign_out ->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

        }
        binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}