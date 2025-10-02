package com.example.practicaviewmodels.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

enum class AppScreen {
    Inicio,
    VerRecetas,
    AgregarReceta,
    Buscar
}

private fun AppScreen.defaultTitle(): String = when (this) {
    AppScreen.Inicio -> "Inicio"
    AppScreen.VerRecetas -> "Recetas"
    AppScreen.AgregarReceta -> "Añadir receta"
    AppScreen.Buscar -> "Buscar"
}

data class MenuBarItem(
    val label: String,
    val onClick: () -> Unit,
    val icon: @Composable () -> Unit
)

@Composable
fun MenuBar(
    currentScreen: AppScreen,
    modifier: Modifier = Modifier,
    title: String? = null,
    showBack: Boolean = false,
    onBack: (() -> Unit)? = null,
    onInicio: (() -> Unit)? = null,
    onVerRecetas: (() -> Unit)? = null,
    onAgregarReceta: (() -> Unit)? = null,
    onBuscarReceta: (() -> Unit)? = null,
    extraItems: List<MenuBarItem> = emptyList()
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showBack) {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menú")
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = title ?: currentScreen.defaultTitle(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(16.dp)
                    )
                    .animateContentSize(),
                offset = DpOffset(x = 8.dp, y = 4.dp)
            ) {
                @Composable
                fun item(
                    visible: Boolean,
                    icon: @Composable () -> Unit,
                    text: String,
                    action: (() -> Unit)?
                ) {
                    if (!visible) return
                    DropdownMenuItem(
                        leadingIcon = icon,
                        text = { Text(text) },
                        onClick = {
                            expanded = false
                            action?.invoke()
                        }
                    )
                }

                item(
                    visible = currentScreen != AppScreen.Inicio,
                    icon = { Icon(Icons.Filled.Home, null) },
                    text = "Inicio",
                    action = onInicio
                )
                item(
                    visible = currentScreen != AppScreen.VerRecetas,
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    text = "Ver recetas",
                    action = onVerRecetas
                )
                item(
                    visible = currentScreen != AppScreen.AgregarReceta,
                    icon = { Icon(Icons.Filled.Add, null) },
                    text = "Añadir receta",
                    action = onAgregarReceta
                )
                item(
                    visible = currentScreen != AppScreen.Buscar,
                    icon = { Icon(Icons.Filled.Search, null) },
                    text = "Buscar",
                    action = onBuscarReceta
                )

                val anyBaseVisible = listOf(
                    currentScreen != AppScreen.Inicio,
                    currentScreen != AppScreen.VerRecetas,
                    currentScreen != AppScreen.AgregarReceta,
                    currentScreen != AppScreen.Buscar
                ).any { it }

                if (extraItems.isNotEmpty() && anyBaseVisible) {
                    HorizontalDivider()
                }
                extraItems.forEach { mi ->
                    item(
                        visible = true,
                        icon = { mi.icon() },
                        text = mi.label,
                        action = mi.onClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "MenuBar - Inicio")
@Composable
private fun MenuBarPreviewInicio() {
    MaterialTheme {
        Column {
            MenuBar(
                currentScreen = AppScreen.Inicio,
                onInicio = {},
                onVerRecetas = {},
                onAgregarReceta = {},
                onBuscarReceta = {}
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Contenido inicio...",
                modifier = Modifier.padding(16.dp),
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, name = "MenuBar - VerRecetas")
@Composable
private fun MenuBarPreviewVerRecetas() {
    MaterialTheme {
        MenuBar(
            currentScreen = AppScreen.VerRecetas,
            onInicio = {},
            onVerRecetas = {},
            onAgregarReceta = {},
            onBuscarReceta = {}
        )
    }
}
