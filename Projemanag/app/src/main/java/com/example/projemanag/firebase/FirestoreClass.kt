package com.example.projemanag.firebase

import android.app.Activity
import android.util.Log
import com.example.projemanag.activities.MainActivity
import com.example.projemanag.activities.SignInActivity
import com.example.projemanag.activities.SignUpActivity
import com.example.projemanag.models.User
import com.example.projemanag.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


// Tutte questi informazioni dei metodi sono presenti nella documentazione.

// Per immagazzinare i dati nel db del cloud in maniera tal, se vogliamo cambiare esempio foto del profilo, lo possiamo fare.
class FirestoreClass {


    private val mFireStore = FirebaseFirestore.getInstance()


    // Invece di crearlo da firebase lo stiamo creando programmaticamente in base i parametri dell' User
    // (ovvero il modelo che abbiamo creato)
    // Invece di andare su firebase -> firebase database -> avvia raccolta e tutti i parametri che mettiamo.
    fun registerUser(activity: SignUpActivity, userInfo: User){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
    }


    // .document ha tutte le informazione dell'utente attuale. uid
    fun signInUser(activity: Activity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                // il metodo toObject far vientare  l'utente in una classe User
                val loggedInUser = document.toObject(User::class.java)

                // Quando effettuiamo il signin che attivity gli stiamo passando.
                when(activity){
                    is SignInActivity -> {
                        if (loggedInUser != null) {
                            activity.signInSuccess(loggedInUser)
                        }
                    }is MainActivity -> {
                            activity.updateNavigationUserDetails(loggedInUser!!)
                    }


                }



            }.addOnFailureListener{

               e ->
                when(activity){
                    is SignInActivity -> {
                        activity.hideProgressDialog()
                    }is MainActivity -> {
                    activity.hideProgressDialog()
                }


                }
                Log.e("SignInUser", "Error writing document", e)
            }
    }



    // Da uid unico dell'utente attuale. (Vedere autentication su firebase.)
    fun getCurrentUserId(): String{

        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

}