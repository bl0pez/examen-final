package com.touristmap.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.touristmap.Screen
import com.touristmap.database.Place
import com.touristmap.viewModel.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FormScreen(navController: NavController, viewModel: PlacesViewModel) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var name by remember { mutableStateOf("") }
    var visitOrder by remember { mutableStateOf(0) }
    var imageUrl by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("-18.497318") }
    var longitude by remember { mutableStateOf("-70.293269") }
    var accommodationCost by remember { mutableStateOf("") }
    var transportationCost by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TouristMap") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.HOME.route)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
                )
        },
        content = { innerPadding ->

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                }
                item {
                    TextField(value = visitOrder.toString(), onValueChange = { visitOrder = it.toIntOrNull() ?: 0 }, label = { Text("Visit Order") })
                }
                item {
                    TextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("Image URL") })
                }
                item {
                    TextField(value = latitude, onValueChange = { latitude = it }, label = { Text("Latitude") })
                }
                item {
                    TextField(value = longitude, onValueChange = { longitude = it }, label = { Text("Longitude") })
                }
                item {
                    TextField(value = accommodationCost, onValueChange = { accommodationCost = it }, label = { Text("Accommodation Cost") })
                }
                item {
                    TextField(value = transportationCost, onValueChange = { transportationCost = it }, label = { Text("Transportation Cost") })
                }
                item {
                    TextField(value = comments, onValueChange = { comments = it }, label = { Text("Comments") })
                }
                item {
                    Button(onClick = {
                        viewModel.createPlace(
                            Place(
                                uid = 0,
                                name = name,
                                visitOrder = visitOrder,
                                imageUrl = imageUrl,
                                latitude = latitude.toDoubleOrNull() ?: 0.0,
                                longitude = longitude.toDoubleOrNull() ?: 0.0,
                                accommodationCost = accommodationCost.toDoubleOrNull() ?: 0.0,
                                transportationCost = transportationCost.toDoubleOrNull(),
                                comments = comments
                            )
                        )
                        navController.navigate(Screen.HOME.route)
                    }) {
                        Text("Add Place")
                    }
                }
            }
        }
    )
}