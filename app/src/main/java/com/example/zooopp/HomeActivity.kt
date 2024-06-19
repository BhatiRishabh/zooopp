package com.example.zooopp

import com.example.zooopp.databinding.ActivityHomeBinding


import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.*

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var map: GoogleMap
    private lateinit var placesClient: PlacesClient
    private var selectedMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMap()
        setupPlaceAutocomplete()

        // Car type selection
        binding.linearSedan.setOnClickListener {
            selectCarType(it, "Sedan")
        }
        binding.linearHatchback.setOnClickListener {
            selectCarType(it, "Hatchback")
        }
        binding.linearLuxury.setOnClickListener {
            selectCarType(it, "Luxury")
        }

        // Time pickers
        binding.spinnerTimeStart.setOnClickListener { showTimePickerDialog(binding.spinnerTimeStart) }
        binding.spinnerTimeEnd.setOnClickListener { showTimePickerDialog(binding.spinnerTimeEnd) }

        // Close button
        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // Set a default location (e.g., New Delhi) and zoom level
        val defaultLocation = LatLng(28.6139, 77.2090)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
    }

    private fun setupPlaceAutocomplete() {
        // Initialize the SDK
//        Places.initialize(applicationContext, getString(R.string.google_maps_key))
//        // Create a new Places client instance
//        placesClient = Places.createClient(this)
    }

    private fun selectCarType(view: View, carType: String) {
        binding.linearSedan.setBackgroundResource(R.drawable.unselected_car_background)
        binding.linearHatchback.setBackgroundResource(R.drawable.unselected_car_background)
        binding.linearLuxury.setBackgroundResource(R.drawable.unselected_car_background)

        view.setBackgroundResource(R.drawable.selected_car_background)
        Toast.makeText(this, "$carType selected", Toast.LENGTH_SHORT).show()
    }

    private fun showTimePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            textView.text = time
        }, hour, minute, false).show()
    }

    private fun searchLocation(query: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
//            for (prediction in response.autocompletePredictions) {
//                // Display the prediction to the user (e.g., in a list)
//                // You can also get the LatLng of the place
//                val placeId = prediction.placeId
//                val placeFields = listOf(Place.Field.LAT_LNG)
//
//                placesClient.fetchPlace(Place.Field.ID, placeFields).addOnSuccessListener { placeResponse ->
//                    val place = placeResponse.place
//                    val latLng = place.latLng
//                    // Use the LatLng
//                    updateMapLocation(latLng)
//                }.addOnFailureListener { exception ->
//                    exception.printStackTrace()
//                }
//            }
        }.addOnFailureListener { exception ->
            exception.printStackTrace()
        }
    }

    private fun updateMapLocation(latLng: LatLng) {
        selectedMarker?.remove()
        selectedMarker = map.addMarker(MarkerOptions().position(latLng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }
}
