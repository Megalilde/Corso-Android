package com.example.projemanag.activities

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projemanag.R
import com.example.projemanag.adapters.MemberListItemsAdapter
import com.example.projemanag.databinding.ActivityMembersBinding
import com.example.projemanag.firebase.FirestoreClass
import com.example.projemanag.models.Board
import com.example.projemanag.models.User
import com.example.projemanag.utils.Constants

class MembersActivity : BaseActivity() {


    private var binding: ActivityMembersBinding? = null

    private lateinit var mBoardDetails: Board

    private lateinit var mAssignedMembersList: ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembersBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if(intent.hasExtra(Constants.BOARD_DETAIL)){
            mBoardDetails = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().getAssignedMembersListDetails(this,mBoardDetails.assignedTo)
        }
        setupActionBar()

    }

    fun setupMembersList(list: ArrayList<User>){

        mAssignedMembersList = list
        hideProgressDialog()

        binding?.rvMembersList?.layoutManager = LinearLayoutManager(this)
        binding?.rvMembersList?.setHasFixedSize(true)

        val adapter = MemberListItemsAdapter(this,list)
        binding?.rvMembersList?.adapter = adapter

    }

    fun memberDetails(user: User){
        mBoardDetails.assignedTo.add(user.id)
        FirestoreClass().assignMemberToBoard(this,mBoardDetails, user)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_member,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_member ->{
                dialogSearchMember()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialogSearchMember(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_search_member)

        dialog.findViewById<TextView>(R.id.tv_add).setOnClickListener{
            val email = dialog.findViewById<EditText>(R.id.et_email_search_member).text.toString()

            if(email.isNotEmpty()){
                dialog.dismiss()
                showProgressDialog(resources.getString(R.string.please_wait))
                FirestoreClass().getMemberDetails(this, email)
            }else{
                Toast.makeText(this, "Please enter members email address-", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<TextView>(R.id.tv_cancel).setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    fun memberAssignSuccess(user: User){
        hideProgressDialog()
        mAssignedMembersList.add(user)
        setupMembersList(mAssignedMembersList)
    }
}