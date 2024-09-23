package com.touristmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.touristmap.database.PlaceDB
import com.touristmap.screen.FormScreen
import com.touristmap.screen.HomeScreen
import com.touristmap.ui.theme.TouristMapTheme
import com.touristmap.viewModel.PlacesViewModel
import com.touristmap.viewModel.PlacesViewModelFactory

enum class Screen(val route: String) {
    FORM("form"),
    HOME("home")
}

class MainActivity : ComponentActivity() {

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