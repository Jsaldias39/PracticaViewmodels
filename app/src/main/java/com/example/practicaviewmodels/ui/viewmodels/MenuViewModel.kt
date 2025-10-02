package com.example.practicaviewmodels.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MenuViewModel : ViewModel() {
    var selectedOption by mutableStateOf<String?>(null)
        private set

    fun selectOption(option: String) {
        selectedOption = option
    }
}
