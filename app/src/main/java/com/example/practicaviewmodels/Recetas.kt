package com.example.practicaviewmodels

import com.example.practicaviewmodels.ui.viewmodels.Recipe

object Recetas {
    fun ejemplo(): List<Recipe> = listOf(
        Recipe(
            titulo = "Tortilla Española",
            ingredientes = listOf("Huevos", "Patatas", "Cebolla", "Aceite de oliva", "Sal"),
            pasos = "Cortar patatas y cebolla, pochar, añadir huevos batidos y cuajar."
        ),
        Recipe(
            titulo = "Ensalada César",
            ingredientes = listOf("Lechuga", "Pollo", "Croutons", "Queso parmesano", "Aderezo César"),
            pasos = "Mezclar ingredientes y añadir aderezo al final."
        ),
        Recipe(
            titulo = "Pasta al Pesto",
            ingredientes = listOf("Pasta", "Albahaca", "Ajo", "Piñones", "Queso parmesano", "Aceite de oliva"),
            pasos = "Triturar ingredientes del pesto y mezclar con la pasta cocida."
        ),
        Recipe(
            titulo = "Sandia Borracha",
            ingredientes = listOf("Sandia", "Vodka (Preferiblemente con sabor)", "Azucar"),
            pasos = "Perforar en la Sandia Huecos equivalente a la cantidad de botellas de vodka que tienes (maximo 4) y colocar las botellas en los huecos y al congelador."
        )
    )
}
