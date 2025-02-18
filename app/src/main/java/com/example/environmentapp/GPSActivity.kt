package com.example.environmentapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.environmentapp.ui.theme.EnvironmentAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class GPSActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            EnvironmentAppTheme {
                var latitude by remember { mutableStateOf("Desconocido") }
                var longitude by remember { mutableStateOf("Desconocido") }

                LocationScreen(
                    latitude = latitude,
                    longitude = longitude,
                    onRequestLocation = { requestLocationPermission() },
                    getCurrentLocation = { getCurrentLocation { lat, lon ->
                        latitude = lat.toString()
                        longitude = lon.toString()
                    } },
                    onNavigateBack = { finish() }
                )
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getCurrentLocation { lat, lon ->
                    Log.d("GPSActivity", "Permiso concedido. Lat: $lat, Lon: $lon")
                }
            } else {
                Log.e("GPSActivity", "Permiso de ubicación denegado")
            }
        }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation { lat, lon ->
                    Log.d("GPSActivity", "Ubicación obtenida después del permiso: Lat: $lat, Lon: $lon")
                }
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Log.e("GPSActivity", "El usuario ha denegado los permisos anteriormente.")
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun getCurrentLocation(onLocationReceived: (Double, Double) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    Log.d("GPSActivity", "Ubicación obtenida: ${location.latitude}, ${location.longitude}")
                    onLocationReceived(location.latitude, location.longitude)
                } else {
                    Log.e("GPSActivity", "No se pudo obtener la ubicación.")
                }
            }.addOnFailureListener { exception ->
                Log.e("GPSActivity", "Error al obtener ubicación: ${exception.message}")
            }
        } else {
            Log.e("GPSActivity", "Permiso de ubicación no concedido")
        }
    }
}

@Composable
fun LocationScreen(
    latitude: String,
    longitude: String,
    onRequestLocation: () -> Unit,
    getCurrentLocation: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Ubicación GPS", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Latitud: $latitude")
        Text(text = "Longitud: $longitude")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { getCurrentLocation() }) {
            Text("Obtener Ubicación")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigateBack) {
            Text("Regresar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    EnvironmentAppTheme {
        LocationScreen("Desconocido", "Desconocido", {}, {}, {})
    }
}
