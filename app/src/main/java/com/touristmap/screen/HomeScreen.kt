package com.touristmap.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.touristmap.Screen
import com.touristmap.components.DeleteConfirmationDialog
import com.touristmap.components.PlaceItem
import com.touristmap.database.Place
import com.touristmap.viewModel.PlacesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: PlacesViewModel) {

    val places by viewModel.places.observeAsState()

    var showDialog by remember { mutableStateOf(false) }
    var placeToDelete by remember { mutableStateOf<Place?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TouristMap") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        content = { innerPadding ->
          Column(
              modifier = Modifier
                  .padding(innerPadding)
                  .fillMaxSize()) {
              places?.let {
                  LazyColumn(
                      content = {
                          items(places!!) { item: Place ->
                              PlaceItem(
                                  place = item,
                                  onEditClick = {
                                      navController.navigate("${Screen.FORM.route}/${item.uid}")
                                  },
                                  onDeleteClick = {
                                      showDialog = true
                                      placeToDelete = item
                                  },
                                  onDetailClick = {
                                      navController.navigate("${Screen.DETAIL.route}/${item.uid}")
                                  }
                              )
                          }
                      }
                  )

                  if (showDialog) {
                      placeToDelete?.let { place ->
                          DeleteConfirmationDialog(
                              placeName = place.name,
                              onConfirm = {
                                  viewModel.deletePlace(place)
                                  showDialog = false
                              },
                              onDismiss = {
                                  showDialog = false
                              }
                          )
                      }
                  }
              }?: Box(
              ) {
                  Text(
                      textAlign = TextAlign.Center,
                      fontSize = 16.sp,
                      text = "No items yet"
                  )
          }

          }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.FORM.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}