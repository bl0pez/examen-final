package com.touristmap.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touristmap.database.Place
import com.touristmap.database.PlaceDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel(private val placeDao: PlaceDao) : ViewModel() {
    val places: LiveData<List<Place>> = placeDao.findAll()

    fun createPlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.create(place)
        }
    }
}