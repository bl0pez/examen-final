package com.touristmap.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


@Composable
fun MapViewWithLocation(context: Context, latitude: Double, longitude: Double) {
    AndroidView(
        factory = {
            MapView(it).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                org.osmdroid.config.Configuration.getInstance().userAgentValue = context.packageName
                controller.setZoom(15.0)
            }
        },
        update = {
            it.overlays.removeIf { true }
            it.invalidate()

            val geoPoint = GeoPoint(latitude, longitude)
            it.controller.animateTo(geoPoint)

            val marcador = Marker(it)
            marcador.position = geoPoint
            marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            it.overlays.add(marcador)
        },
        modifier = Modifier.fillMaxSize()
    )
}