package com.example.simpleapicalldemo

import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CallAPILoginAsyncTask("Manuel","123456").execute()
    }

    private inner class CallAPILoginAsyncTask(val username: String, val password: String): AsyncTask<Any,Void,String>(){

        // lateinit posticipa l'inizializzazione della variabile
        private lateinit var customProgessDialog: Dialog


        // Prima di conneterci a internet
        override fun onPreExecute() {
            super.onPreExecute()
            showProgessDialog()
        }

        override fun doInBackground(vararg p0: Any?): String {
            var result: String
            var connection: HttpURLConnection? = null
            try {
                val url = URL("https://run.mocky.io/v3/e484d465-bdff-4399-8498-0b3c3a2bb19b")
                connection = url.openConnection() as HttpURLConnection
                // Prendere il dato
                connection.doInput = true
                // Spedire il dato
                connection.doOutput = true

                connection.instanceFollowRedirects = false

                // Per spedire i dati metodo classico
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("charset", "utf-8")
                connection.setRequestProperty("Accept", "application/json")

                connection.useCaches = false

                val writeDateOutputStream = DataOutputStream(connection.outputStream)

                val jsonRequest = JSONObject()
                jsonRequest.put("username", username)
                jsonRequest.put("password", password)

                writeDateOutputStream.writeBytes(jsonRequest.toString())
                writeDateOutputStream.flush()
                writeDateOutputStream.close()



                // Status code 200 400 ecc dipende da come va
                val httpResult: Int = connection.responseCode
                if(httpResult == HttpURLConnection.HTTP_OK){
                    val inputStream = connection.inputStream
                    val reader =  BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder =  StringBuilder()
                    var line: String?
                    try{
                        while(reader.readLine().also { line= it } != null){
                            stringBuilder.append(line+ "\n")
                        }
                    }catch (e: IOException){
                        e.printStackTrace()
                    }finally {
                        try {
                            inputStream.close()
                        }catch (e: IOException){
                            e.printStackTrace()
                        }
                    }
                    result = stringBuilder.toString()
                }else{
                    // Bad request ecc
                    result = connection.responseMessage
                }
            }catch (e: SocketTimeoutException){
                result = "Connection Timeout"
            }catch (e: Exception){
                result = "Error: "+ e.message
            }finally {
                connection?.disconnect()
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            cancelProgessDialog()
            result?.let {
                Log.i("JSON RESPONSE RESULT", it)
            }

            // Qui uso la libreria Gson importata da google (raccomandato)
            val responseData = Gson().fromJson(result,ResponseData::class.java)
            Log.i("Message", responseData.message)
            Log.i("User Id", "${responseData.userId}")
            Log.i("Name", "${responseData.name}")
            Log.i("Email", "${responseData.email}")
            Log.i("Mobile", "${responseData.mobile}")

            Log.i("Is Profile Completed", "${responseData.profile_details.is_profile_completed}")
            Log.i("Rating", "${responseData.profile_details.rating}")

            for (item in responseData.data_list.indices){
                Log.i("Value $item", "${responseData.data_list[item]}")

                Log.i("ID", "${responseData.data_list[item].id}")
                Log.i("Value $item", "${responseData.data_list[item].value}")
            }
            /*
            // Qui utilizzo l'approccio classico, non raccomandato
            val jsonObject = JSONObject(result)
            val message = jsonObject.optString("message")
            Log.i("Message", message)
            val userId = jsonObject.optInt("user_id")
            Log.i("User id", "$userId")
            val name = jsonObject.optString("name")
            Log.i("Name", "$name")

            val profileDetailsObject = jsonObject.optJSONObject("profile_details")
            val isProfileCompleted = profileDetailsObject.optBoolean("is_profile_completed")
            Log.i("Is Profile Completed", "$isProfileCompleted")

            val dataList = jsonObject.optJSONArray("data_list")
            Log.i("Data List Size", "${dataList.length()}")

            for(item in 0 until dataList.length()){
                Log.i("Value $item", "${dataList[item]}")

                var dataItemObject: JSONObject = dataList[item] as JSONObject

                val id = dataItemObject.optInt("id")
                Log.i("ID","$id")

                val value = dataItemObject.optString("value")
                Log.i("Value", "$value")

            }*/
        }


        private fun showProgessDialog(){
            customProgessDialog = Dialog(this@MainActivity)
            customProgessDialog.setContentView(R.layout.dialog_custom_progress)
        }

        private fun cancelProgessDialog(){
            customProgessDialog.dismiss()
        }
    }


}