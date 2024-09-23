package com.touristmap.screen

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.touristmap.database.Place
import com.touristmap.viewModel.PlacesViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    navController: NavController,
    placeId: String,
    viewModel: PlacesViewModel,
    permissionLauncher: ActivityResultLauncher<Array<String>>
) {

    permissionLauncher.launch(
        arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    var place by remember { mutableStateOf<Place?>(null) }

    LaunchedEffect(placeId) {
        place = viewModel.getPlaceById(placeId.toInt())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TouristMap") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                content = {
                    place?.let { lugar ->
                        item {
                            AsyncImage(
                                model = lugar.imageUrl,
                                contentDescription = lugar.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        item {
                            Text(
                                text = lugar.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Costo por noche
                                Text(
                                    text = "Costo x noche",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "$${lugar.accommodationCost}",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Traslados
                                Text(
                                    text = "Traslados",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "$${lugar.transportationCost}",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Comentarios:",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = lugar.comments,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }

                        item {
                            AndroidView(factory = { context ->
                                MapView(context).apply {

                                    org.osmdroid.config.Configuration.getInstance().userAgentValue = context.packageName

                                    setTileSource(TileSourceFactory.MAPNIK)
                                    controller.setZoom(15.0)
                                    setMultiTouchControls(true)


                                    controller.setCenter(GeoPoint(-33.4489, -70.6693))

                                    val marker = Marker(this)
                                    marker.position = GeoPoint(-33.4489, -70.6693)
                                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    overlays.add(marker)
                                }
                            }, modifier = Modifier.height(200.dp))
                        }
                    }
                }
            )
        }
    )

}