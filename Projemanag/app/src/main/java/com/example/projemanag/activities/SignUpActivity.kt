package com.example.projemanag.activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projemanag.R
import com.example.projemanag.databinding.ActivitySignUpBinding
import com.example.projemanag.firebase.FirestoreClass
import com.example.projemanag.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignUpActivity : BaseActivity() {

    private var binding: ActivitySignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        setupActionBar()
        binding?.btnSignUp?.setOnClickListener{
            registerUser()
        }
    }


    fun userRegisteredSuccess(){
        Toast.makeText(
            this,
            "You have successfully registered",
            Toast.LENGTH_LONG,
        ).show()
        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
    }


    private fun setupActionBar(){
        setSupportActionBar(binding?.toolbarSignUpActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        binding?.toolbarSignUpActivity?.setNavigationOnClickListener { onBackPressed() }
    }


    // Trim rimuove il carattere che viene magari inconsapevolmente messo
    // Esegue la registrazione
    private fun registerUser(){
        val name: String = binding?.etName?.text.toString().trim { it <= ' '}
        val email: String = binding?.etEmail?.text.toString().trim{ it <= ' '}
        val password: String = binding?.etPassword?.text.toString().trim{ it <= ' '}

        if(validateForm(name,email,password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val user = User(firebaseUser.uid,name,registeredEmail)

                    // Molto importanti permette di aggiungere i campi di User nella collezione.
                    // per immagazzinare i dati.
                    FirestoreClass().registerUser(this,user)

                } else {
                    Log.i("Email: ", "${task.exception!!.message}")
                    Toast.makeText(
                        this,
                        "Registration failed",
                        Toast.LENGTH_LONG,
                    ).show()

                }
            }
        }
    }


    // Questo serve per validare il form, se è vuoto. Esso viene utilizzato nella fase di registrazione.
    private fun validateForm(name: String, email: String, password: String): Boolean{
        return when{
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter a email address")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }else -> true
        }

    }

}