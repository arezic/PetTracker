package com.antoniosoftware.pettracker

import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.collections.GroundOverlayManager
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.collections.PolygonManager
import com.google.maps.android.collections.PolylineManager
import com.google.maps.android.data.kml.KmlContainer
import com.google.maps.android.data.kml.KmlLayer
import com.google.maps.android.ktx.utils.kml.kmlLayer

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        val markerManager = MarkerManager(googleMap)
        val groundOverlayManager = GroundOverlayManager(googleMap)
        val polygonManager = PolygonManager(googleMap)
        val polylineManager = PolylineManager(googleMap)


        //addKml(googleMap, markerManager, polylineManager, polygonManager, groundOverlayManager)

        val split = LatLng(43.508133, 16.440193)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(split))


        var polylineOptions = PolylineOptions()

        googleMap.addMarker(MarkerOptions().position(LatLng(Supplier.locations[0].latitude,Supplier.locations[0].longitude)).title("Start"))
        googleMap.addMarker(MarkerOptions().position(LatLng(Supplier.locations.last().latitude,Supplier.locations.last().longitude)).title("Finish"))
        for (location in Supplier.locations) {
            val latLong = LatLng(location.latitude,location.longitude)
            polylineOptions.add(latLong)
        }

        var polyline = googleMap.addPolyline(polylineOptions)

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(Supplier.locations[0].latitude,Supplier.locations[0].longitude),12.0F))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
    private fun addKml(
        map: GoogleMap,
        markerManager: MarkerManager,
        polylineManager: PolylineManager,
        polygonManager: PolygonManager,
        groundOverlayManager: GroundOverlayManager
    ) {
        // KML Polyline
        val kmlPolylineLayer = kmlLayer(
            map = map,
            resourceId = R.raw.south_london_line_kml,
            context = this.requireContext(),
            markerManager = markerManager,
            polygonManager = polygonManager,
            polylineManager = polylineManager,
            groundOverlayManager = groundOverlayManager
        )
        kmlPolylineLayer.addLayerToMap()
        kmlPolylineLayer.setOnFeatureClickListener { feature ->
            Toast.makeText(
                this.requireContext(),
                "KML polyline clicked: " + feature.getProperty("name"),
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}