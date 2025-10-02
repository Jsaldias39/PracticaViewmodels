package com.example.practicaviewmodels.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color   // <- Import añadido
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practicaviewmodels.ui.viewmodels.Recipe

@Composable
fun AñadirRecetas(
    onBack: () -> Unit,
    onGuardar: (Recipe) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var preparacion by rememberSaveable { mutableStateOf("") }
    var ingredientes by rememberSaveable { mutableStateOf(listOf(TextFieldValue(""))) }

    val puedeGuardar = nombre.isNotBlank() &&
            preparacion.isNotBlank() &&
            ingredientes.any { it.text.isNotBlank() }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .padding(bottom = 110.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Nombre de la receta", style = MaterialTheme.typography.labelSmall)
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 44.dp)
                    .padding(top = 4.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(18.dp))

            Text("Ingredientes", style = MaterialTheme.typography.labelSmall)
            ingredientes.forEachIndexed { index, value ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = { nuevo ->
                            ingredientes = ingredientes.toMutableList().also { it[index] = nuevo }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 44.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodySmall,
                        shape = RoundedCornerShape(12.dp)
                    )
                    if (index == ingredientes.lastIndex) {
                        Spacer(Modifier.width(8.dp))
                        OutlinedButton(
                            onClick = { ingredientes = ingredientes + TextFieldValue("") },
                            modifier = Modifier
                                .height(44.dp)
                                .width(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Añadir ingrediente")
                        }
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            Text("Preparación", style = MaterialTheme.typography.labelMedium)
            OutlinedTextField(
                value = preparacion,
                onValueChange = { preparacion = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 160.dp)
                    .padding(top = 6.dp),
                shape = RoundedCornerShape(18.dp),
                singleLine = false,
                maxLines = 12
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) { Text("Volver") }

            Button(
                onClick = {
                    val listaIngredientes = ingredientes.map { it.text.trim() }.filter { it.isNotEmpty() }
                    onGuardar(
                        Recipe(
                            titulo = nombre.trim(),
                            ingredientes = listaIngredientes,
                            pasos = preparacion.trim()
                        )
                    )
                },
                enabled = puedeGuardar,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                )
            ) { Text("Guardar") }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "AñadirRecetas Preview")
@Composable
private fun AñadirRecetasPreview() {
    MaterialTheme { AñadirRecetas(onBack = {}, onGuardar = {}) }
}
