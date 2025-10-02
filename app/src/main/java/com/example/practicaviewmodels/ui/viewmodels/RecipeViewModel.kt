package com.example.practicaviewmodels.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.practicaviewmodels.Recetas
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Recipe(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val ingredientes: List<String> = emptyList(),
    val pasos: String = ""
)

class RecipeViewModel : ViewModel() {

    private val _recetas = MutableStateFlow<List<Recipe>>(emptyList())
    val recetas: StateFlow<List<Recipe>> = _recetas

    init {
        if (_recetas.value.isEmpty()) {
            _recetas.value = Recetas.ejemplo()
        }
    }

    fun agregar(titulo: String, ingredientes: List<String>, pasos: String) {
        val nueva = Recipe(titulo = titulo, ingredientes = ingredientes, pasos = pasos)
        _recetas.value = _recetas.value + nueva
    }

    fun actualizar(receta: Recipe) {
        _recetas.value = _recetas.value.map { if (it.id == receta.id) receta else it }
    }

    fun eliminar(id: String) {
        _recetas.value = _recetas.value.filterNot { it.id == id }
    }
}
