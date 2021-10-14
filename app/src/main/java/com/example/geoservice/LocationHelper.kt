package com.example.geoservice

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat

class LocationHelper : LocationListener {
    companion object {
        var Imhere: Location? = null
        fun startLocationListener(context: Context) {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission( //проверка разрешений о запросе локации
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            locationManager.requestLocationUpdates( //запрос на локацию
                LocationManager.GPS_PROVIDER,
                500L,
                10F,
                LocationHelper()
            )
            Imhere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) //устанавливаем локацию в переменную Imhere
        }
    }

    override fun onLocationChanged(location: Location) {
        // Imhere=location
    }
}