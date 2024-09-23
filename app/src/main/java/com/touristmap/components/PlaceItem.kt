package com.touristmap.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.touristmap.database.Place

@Composable
fun PlaceItem(place: Place) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(6.dp)
    ) {
        Column {
            AsyncImage(
                model = place.imageUrl,
                contentDescription = place.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {

                Text(text = place.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Costo x noche: $${place.accommodationCost}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Traslado: $${place.transportationCost}", style = MaterialTheme.typography.bodySmall)
                
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = "Accommodation Cost")
                    }

                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Place, contentDescription = "Edit")
                    }

                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }

                }
            }
        }
    }
}