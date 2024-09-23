package com.touristmap.viewModel

import androidx.lifecycle.ViewModel

class PermissionViewModel: ViewModel() {
    var onCameraPermissionGranted: () -> Unit = {}
}

