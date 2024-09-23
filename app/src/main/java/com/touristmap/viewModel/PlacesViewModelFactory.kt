package com.touristmap.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.touristmap.database.PlaceDao

class PlacesViewModelFactory(private val placeDao: PlaceDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlacesViewModel::class.java)) {
            return PlacesViewModel(placeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}