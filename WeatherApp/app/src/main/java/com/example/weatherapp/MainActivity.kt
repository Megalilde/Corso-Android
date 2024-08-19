package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.network.WeatherService
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.file.WatchService
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var mProgressDialog: Dialog? = null

    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        // SharedPreferences
        mSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)


        setupUI()

        if (!isLocationEnabled()) {
            Toast.makeText(this, "Your location provider is turned off. Please turn it on", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Your location provider is already ON", Toast.LENGTH_SHORT).show()

            Dexter.withActivity(this).withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object : MultiplePermissionsListener {

                @RequiresApi(Build.VERSION_CODES.S)
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report != null && report.areAllPermissionsGranted()) {
                        requestLocationData()
                    }
                    if (report != null && report.isAnyPermissionPermanentlyDenied) {
                        Toast.makeText(
                            this@MainActivity,
                            "You have denied location permission. Please allow it as it is mandatory.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationaleDialogForPermissions()
                }
            }).onSameThread().check()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingPermission")
    private fun requestLocationData() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        mFusedLocationClient.requestLocationUpdates(
            locationRequest, mLocationCallback, Looper.getMainLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            val latitude = lastLocation.latitude
            Log.i("Current Latitude", "$latitude")

            val longitude = lastLocation.longitude
            Log.i("Current Longitude", "$longitude")
            getLocationWeatherDetails(latitude,longitude)
        }
    }

    private fun getLocationWeatherDetails(latitude: Double, longitude: Double){
        if(Constants.isNetworkAvailable(this)){
            // Vedere la documentazione di retrofit
            // addConverter perchè li dobbiamo convertire nel formato giusto.
            // Passiamo la url di base
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
                GsonConverterFactory.create()).build()

            // Settiamo il service, ovvero le query della chiamata e cosa ci restituisce.
            val service: WeatherService = retrofit.create<WeatherService>(WeatherService::class.java)

            // Con il settaggio del service eseguiamo la chiamata fatta con enqueue()
            val listCall: Call<WeatherResponse> = service.getWeather(
                latitude, longitude, Constants.METRIC_UNIT, Constants.APP_ID
            )
            showCustomProgressDialog()


            listCall.enqueue(object: Callback<WeatherResponse>{
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if(response!!.isSuccessful){

                        hideProgressDialog()
                        val weatherList: WeatherResponse? = response.body()

                        // Lo converto in json e lo immagazzino nello SharedPreferences
                        val weatherResponseJsonString = Gson().toJson(weatherList)
                        Log.i("Buba", "$weatherResponseJsonString")
                        val editor = mSharedPreferences.edit()
                        editor.putString(Constants.WEATHER_RESPONSE_DATA,weatherResponseJsonString)
                        editor.apply()


                        setupUI()
                        Log.i("Response Result", "$weatherList")
                    } else{
                        val rc = response.code()
                        when(rc){
                            400 -> Log.e("Error 400", "Bad Request - Check your request parameters.")
                            401 -> Log.e("Error 401", "Unauthorized - Check your API key.")
                            403 -> Log.e("Error 403", "Forbidden - You might not have access.")
                            404 -> Log.e("Error 404", "Not Found - The endpoint might be wrong.")
                            500 -> Log.e("Error 500", "Internal Server Error - Problem with the server.")
                            else -> Log.e("Error", "Unexpected error: $rc")
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e("Errorrrrrrrr", t!!.message.toString())
                }

            })
        } else {
            Toast.makeText(this,
                "No internet connection available",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showRationaleDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showCustomProgressDialog(){
        mProgressDialog = Dialog(this)
        mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)
        mProgressDialog!!.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_refresh ->{
                requestLocationData()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun hideProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog!!.dismiss()
        }
    }

    private fun setupUI(){
        val weatherResponseJsonString = mSharedPreferences.getString(Constants.WEATHER_RESPONSE_DATA, "")
        if (!weatherResponseJsonString.isNullOrEmpty()){
            val weatherList = Gson().fromJson(weatherResponseJsonString, WeatherResponse::class.java)

            for(i in weatherList.weather.indices){
                Log.i("Weather Name", weatherList.weather.toString())
                binding?.tvMain?.text = weatherList.weather[i].main
                binding?.tvMainDescription?.text = weatherList.weather[i].description

                binding?.tvTemp?.text = weatherList.main.temp.toString() + getUnit(application.resources.configuration.toString())
                binding?.tvHumidity?.text = weatherList.main.humidity.toString() + "%"

                binding?.tvMin?.text = weatherList.main.temp_min.toString() + " min"
                binding?.tvMax?.text = weatherList.main.temp_max.toString() + " max"

                binding?.tvSpeed?.text = weatherList.wind.speed.toString()

                binding?.tvName?.text = weatherList.name
                binding?.tvCountry?.text = weatherList.sys.country

                binding?.tvSunsetTime?.text = unixTime(weatherList.sys.sunset)
                binding?.tvSunriseTime?.text = unixTime(weatherList.sys.sunrise)

                when(weatherList.weather[i].icon){
                    "01d" -> binding?.ivMain?.setImageResource(R.drawable.sunny)
                    "02d" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "03d" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "04d" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "04n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "10d" -> binding?.ivMain?.setImageResource(R.drawable.rain)
                    "11d" -> binding?.ivMain?.setImageResource(R.drawable.storm)
                    "13d" -> binding?.ivMain?.setImageResource(R.drawable.snowflake)
                    "01n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "02n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "03n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "10n" -> binding?.ivMain?.setImageResource(R.drawable.cloud)
                    "11n" -> binding?.ivMain?.setImageResource(R.drawable.rain)
                    "13n" -> binding?.ivMain?.setImageResource(R.drawable.snowflake)
                }

            }
        }


    }

    private fun unixTime(timex: Long): String?{
        val date = Date(timex * 1000L)
        val sdf = SimpleDateFormat("HH:mm", Locale.UK)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }


    private fun getUnit(value: String): String?{
        var value = "°C"
        if("US" == value || "LR" == value || "MM" == value){
            value = "°F"
        }
        return value
    }

}
