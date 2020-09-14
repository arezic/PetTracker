package com.antoniosoftware.pettracker

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupData()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navigationController = findNavController(R.id.fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.petFragment,R.id.activityFragment,R.id.mapsFragment))
        setupActionBarWithNavController(navigationController,appBarConfiguration)

        bottomNavigationView.setupWithNavController(navigationController)
    }

    private fun setupData()
    {
        for (x in 0..5)
            Supplier.locations.add(Location("dummyprovider"))
        Supplier.locations[0].latitude = 43.508133
        Supplier.locations[0].longitude = 16.440193
        Supplier.locations[0].time = 0
        Supplier.locations[1].latitude = 43.600
        Supplier.locations[1].longitude = 16.500
        Supplier.locations[1].time = 1000000
        Supplier.locations[2].latitude = 43.777
        Supplier.locations[2].longitude = 17.000
        Supplier.locations[2].time = 2000000
        Supplier.locations[3].latitude = 43.888
        Supplier.locations[3].longitude = 16.440193
        Supplier.locations[3].time = 3000000
        Supplier.locations[4].latitude = 43.888
        Supplier.locations[4].longitude = 16.500
        Supplier.locations[4].time = 4000000
        Supplier.locations[5].latitude = 43.900
        Supplier.locations[5].longitude = 16.500
        Supplier.locations[5].time = 5000000
        Supplier.setSpeeds()
    }
}