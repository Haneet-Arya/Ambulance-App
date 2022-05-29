package com.haneetarya.ambulanceapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.haneetarya.ambulanceapp.databinding.ActivityNearestHospitalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NearestHospital : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityNearestHospitalBinding
    private val hospitals = ArrayList<HospitalModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNearestHospitalBinding.inflate(layoutInflater)
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
                    mMap.addMarker(MarkerOptions().position(LatLng(hospitals[0].lat,hospitals[0].lng)))
                    val handler = Handler()
                    handler.postDelayed(Runnable {
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    hospitals[0].lat,
                                    hospitals[0].lng
                                ), 15f
                            )
                        )
                    }, 200)
                    binding.hospitalName.text=hospitals[0].name
                }
            }

            override fun onFailure(call: Call<ArrayList<HospitalModel>>, t: Throwable) {
                Toast.makeText(applicationContext,"Network Error",Toast.LENGTH_LONG).show()
            }

        })


    }
}