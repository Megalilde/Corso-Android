package com.example.projemanag.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.projemanag.activities.CreateBoardActivity
import com.example.projemanag.activities.MainActivity
import com.example.projemanag.activities.MyProfileActivity
import com.example.projemanag.activities.SignInActivity
import com.example.projemanag.activities.SignUpActivity
import com.example.projemanag.activities.TaskListActivity
import com.example.projemanag.models.Board
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

    // Simile a quello di sopra solo per la board
    fun createBoard(activity: CreateBoardActivity, board:Board){
        mFireStore.collection(Constants.BOARDS)
            .document()
            .set(board, SetOptions.merge())
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Board created sucessfully.")

                Toast.makeText(activity, "Board created successfully.", Toast.LENGTH_LONG).show()

                activity.boardCreatedSuccessifully()
            }.addOnFailureListener {
                exception ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board.", exception)
            }

    }


    // .document ha tutte le informazione dell'utente attuale. uid
    // Serve per caricare i dati da firestore sul codice.
    fun loadUserData(activity: Activity, readBoardsList: Boolean = false){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                // il metodo toObject per far diventare l'utente in una classe User // MOLTO IMPORTANTE!
                val loggedInUser = document.toObject(User::class.java)

                // Quando effettuiamo il signin che attivity gli stiamo passando.
                when(activity){
                    is SignInActivity -> {
                        if (loggedInUser != null) {
                            activity.signInSuccess(loggedInUser)
                        }
                    }is MainActivity -> {
                            activity.updateNavigationUserDetails(loggedInUser!!, readBoardsList)
                    }
                    is MyProfileActivity ->{
                        activity.setUserDataInUI(loggedInUser!!)
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


    // Prende dallo storage lo storage by id // nota qui e stato messo whereArrayContains
    fun getBoardsList(activity: MainActivity){
        mFireStore.collection(Constants.BOARDS)
            .whereArrayContains(Constants.ASSIGNED_TO,getCurrentUserId())
            .get()
            .addOnSuccessListener {
                document ->
                Log.i(activity.javaClass.simpleName, document.documents.toString())
                val boardList: ArrayList<Board> = ArrayList()
                for (i in document.documents){
                    // MOLTO IMPORTANTE!
                    val board = i.toObject(Board::class.java)!!
                    board.documentId = i.id
                    boardList.add(board)
                }
                activity.populateBoardsListToUI(boardList)
            }.addOnFailureListener {
                e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board", e)

        }

    }

    // Permette di caricare i dati su firebase
    fun updateUserProfileDate(activity: MyProfileActivity, userHashMap: HashMap<String, Any>){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.i(activity.javaClass.simpleName, "Profile Data update")
                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_LONG).show()

                // Fa sparire la dialog
                activity.profileUpdateSuccess()
            }.addOnFailureListener {
                e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board", e)
                Toast.makeText(activity, "Error when updating the profile!", Toast.LENGTH_LONG).show()
            }
    }


    // Restituisce uid unico dell'utente attuale. (Vedere autentication su firebase.)
    fun getCurrentUserId(): String{

        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }


    // Aggiunge i task nella board associata.
    fun addUpdateTaskList(activity: TaskListActivity, board: Board){
        val taskListHashMap = HashMap<String, Any>()
        taskListHashMap[Constants.TASK_LIST] = board.taskList

        mFireStore.collection(Constants.BOARDS)
            .document(board.documentId)
            .update(taskListHashMap)
            .addOnSuccessListener {
                Log.i(activity.javaClass.simpleName, "TaskList updated successfully")
                activity.addUpdateTaskListSuccess()
            }.addOnFailureListener {
                exception ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board", exception)
            }
    }


    // Prende i task relativi all'id del boardDocumentId
    fun getBoardsDetails(activity: TaskListActivity, documentId: String) {
        mFireStore.collection(Constants.BOARDS)
            .document(documentId)
            .get()
            .addOnSuccessListener {
                    document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                // MOLTO IMPORTANTE! Guarda altri MOLTO IMPORTANTE!
                val board = document.toObject(Board::class.java)!!
                board.documentId = document.id
                activity.boardDetails(board)

            }.addOnFailureListener {
                    e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while creating a board", e)

            }
    }

}