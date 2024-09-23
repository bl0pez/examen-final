package com.touristmap.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touristmap.database.Place
import com.touristmap.database.PlaceDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlacesViewModel(private val placeDao: PlaceDao) : ViewModel() {
    val places: LiveData<List<Place>> = placeDao.findAll()

    suspend fun getPlaceById(placeId: Int): Place? {
        return placeDao.findPlaceById(placeId)
    }


    fun updatePlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.update(place)
        }
    }

    fun createPlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.create(place)
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            placeDao.delete(place)
        }
    }
}