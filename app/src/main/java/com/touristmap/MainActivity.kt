package com.touristmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.touristmap.database.PlaceDB
import com.touristmap.screen.FormScreen
import com.touristmap.screen.HomeScreen
import com.touristmap.screen.PlaceDetailScreen
import com.touristmap.ui.theme.TouristMapTheme
import com.touristmap.viewModel.PermissionViewModel
import com.touristmap.viewModel.PlacesViewModel
import com.touristmap.viewModel.PlacesViewModelFactory

enum class Screen(val route: String) {
    FORM("form"),
    HOME("home"),
    DETAIL("detail")
}

class MainActivity : ComponentActivity() {

    val permissionViewModel: PermissionViewModel by viewModels()

    val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if(
            (it[android.Manifest.permission.ACCESS_FINE_LOCATION]?:false) or
            (it[android.Manifest.permission.ACCESS_COARSE_LOCATION]?:false)
        ) {
            permissionViewModel.onCameraPermissionGranted()
        }
    }

    private lateinit var placeViewModel: PlacesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa tu base de datos
        val placeDao = PlaceDB.getDatabase(applicationContext).placeDao()
        val factory = PlacesViewModelFactory(placeDao)

        placeViewModel = ViewModelProvider(this, factory)[PlacesViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            TouristMapTheme {

                val navController = rememberNavController()

                NavHost(navController, startDestination = Screen.HOME.route) {
                    composable(Screen.HOME.route) { HomeScreen(navController, placeViewModel) }
                    composable(Screen.FORM.route) { FormScreen(navController, placeViewModel, placeId = null) }
                    composable("${Screen.DETAIL.route}/{placeId}") { backStackEntry ->
                        val placeId = backStackEntry.arguments?.getString("placeId")
                        placeId?.let {
                            PlaceDetailScreen(navController, it, placeViewModel, permissionLauncher)
                        }
                    }
                    composable(
                        route = "${Screen.FORM.route}/{placeId}",
                        arguments = listOf(navArgument("placeId") {type = NavType.IntType })
                    ) { backStackEntry ->
                        val placeId = backStackEntry.arguments?.getInt("placeId")
                        FormScreen(navController, placeViewModel, placeId)
                    }
                }
            }
        }
    }
}