package com.example.prosjekt

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.prosjekt.PermissionUtils.isPermissionGranted
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.ClusterManager.OnClusterItemClickListener


class MapsFragment : Fragment(),GoogleMap.OnMyLocationButtonClickListener,
GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    private var permissionDenied = false
    private lateinit var map: GoogleMap
    private lateinit var clusterManager: ClusterManager<Pin>
    private lateinit var card: LocationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)
        card = LocationFragment("maps")
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment!!.getMapAsync {
            map = it
            map.uiSettings.isZoomControlsEnabled = true
            map.setOnMyLocationButtonClickListener(this)
            map.setOnMyLocationClickListener(this)
            setUpClusterer()
            enableMyLocation()
        }

        val terrainButton = rootView.findViewById(R.id.terrain_button) as ImageButton
        terrainButton.setOnClickListener{
            println("TERRAIN BUTTON WORKS")
            if(map.mapType == GoogleMap.MAP_TYPE_SATELLITE){
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
            } else{
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
        }

        return rootView
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {

        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true

            return
        }

        // 2. If if a permission rationale dialog should be shown
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            PermissionUtils.RationaleDialog.newInstance(
                MapsFragment.LOCATION_PERMISSION_REQUEST_CODE, true
            ).show(childFragmentManager, "dialog")
            return
        }

        // 3. Otherwise, request permission
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            MapsFragment.LOCATION_PERMISSION_REQUEST_CODE
        )
        enableMyLocation()
    }
    override fun onMyLocationButtonClick(): Boolean {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != MapsFragment.LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
            return
        }

        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    @SuppressLint("MissingPermission")
    private fun setUpClusterer() {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(64.5, 11.0), 4.8f))
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = ClusterManager(activity, map)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)

        clusterManager.setOnClusterItemClickListener(OnClusterItemClickListener<Pin> {
            for (place: FishingPlace in (activity as MainActivity).getPlaces()){
                if (place.name==it.title){
                    //Start location fragment  her
                    (activity as MainActivity).getWater(place)
                    (activity as MainActivity).getSunrise(place)
                    (activity as MainActivity).getCurrentWeather(place)

                }
            }


            false
        })
        // Add cluster items (markers) to the cluster manager.
        addItems()
    }

    //getting data about fishing places and creating markers on a map
    private fun addItems() {
        var count = 0
        for(result: FishingPlace in (activity as MainActivity).getPlaces()) {
            val offsetItem = Pin(result.north, result.east, "${result.name}", "")
            clusterManager.addItem(offsetItem)
            count++
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        return false
    }

    //class for markers on a map
    inner class Pin(
        lat: Double,
        lng: Double,
        title: String,
        snippet: String
    ) : ClusterItem {

        private val position: LatLng
        private val title: String
        private val snippet: String

        override fun getPosition(): LatLng {
            return position
        }

        override fun getTitle(): String? {
            return title
        }

        override fun getSnippet(): String? {
            return snippet
        }


        init {
            position = LatLng(lat, lng)
            this.title = title
            this.snippet = snippet
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}

