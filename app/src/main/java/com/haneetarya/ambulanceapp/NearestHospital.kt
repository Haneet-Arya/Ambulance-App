package com.haneetarya.ambulanceapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.haneetarya.ambulanceapp.databinding.ActivityNearestHospitalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NearestHospital : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityNearestHospitalBinding
    private val hospitals = ArrayList<HospitalModel>()
    private val drivers = ArrayList<DriverModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNearestHospitalBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContentView(binding.root)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }
                else -> {
                    // No location access granted.

                }
            }
        }

        binding.back.setOnClickListener{
            finish()
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))

        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isMyLocationEnabled=true
        mMap.getUiSettings().isMyLocationButtonEnabled = false;
        mMap.getUiSettings().setAllGesturesEnabled(true);

        // Add a marker in Sydney and move the camera
        val call = RetrofitSingleton.networkCalls.getHospitals()
        call.enqueue(object : Callback<ArrayList<HospitalModel>> {
            override fun onResponse(
                call: Call<ArrayList<HospitalModel>>,
                response: Response<ArrayList<HospitalModel>>
            ) {
                if(!response.isSuccessful){
                    Toast.makeText(applicationContext,"Some error occurred while fetching the hospital",Toast.LENGTH_LONG).show()
                    return
                }
                response.body()?.let {
                    hospitals.addAll(it)
                    mMap.addMarker(MarkerOptions().position(LatLng(hospitals[0].lat,hospitals[0].lng)).title(hospitals[0].name))

                    binding.hospitalName.text=hospitals[0].name
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location : Location? ->
                            // Got last known location. In some rare situations this can be null.
                            val builder = LatLngBounds.builder()
                            if (location != null) {
                                builder.include(LatLng(location.latitude,location.longitude))
                                builder.include(LatLng(
                                    hospitals[0].lat,
                                    hospitals[0].lng
                                ))
                                val bounds = builder.build()
                                val width = resources.displayMetrics.widthPixels
                                val height = resources.displayMetrics.heightPixels
                                val padding =
                                    (width * 0.10).toInt() // offset from edges of the map 10% of screen


                                val cu = CameraUpdateFactory.newLatLngBounds(
                                    bounds,
                                    width,
                                    height,
                                    padding
                                )

                                mMap.animateCamera(cu)

                            }
                        }
                }
            }

            override fun onFailure(call: Call<ArrayList<HospitalModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"Network Error",Toast.LENGTH_LONG).show()
            }

        })

        val driverCall = RetrofitSingleton.networkCalls.getDrivers()
        driverCall.enqueue(object: Callback<ArrayList<DriverModel>> {
            override fun onResponse(
                call: Call<ArrayList<DriverModel>>,
                response: Response<ArrayList<DriverModel>>
            ) {
                if(!response.isSuccessful){
                    Toast.makeText(applicationContext,"Some error occurred while fetching the hospital",Toast.LENGTH_LONG).show()
                    return
                }
                response.body()?.let { drivers.addAll(it) }
                for(e in drivers){
                    mMap.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                e.lat,
                                e.lng
                            )
                        )
                            .title(e.name)
                            .icon(BitmapFromVector(applicationContext, R.drawable.ic_ambulance))
                    )
                }
            }

            override fun onFailure(call: Call<ArrayList<DriverModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"Network Error",Toast.LENGTH_LONG).show()
            }

        })



    }
    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)


        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}