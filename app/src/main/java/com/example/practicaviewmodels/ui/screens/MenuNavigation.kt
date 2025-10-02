package com.example.practicaviewmodels.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.practicaviewmodels.ui.viewmodels.MenuViewModel

class MenuNavigation {

    data class MenuOption(
        val id: String,
        val label: String,
        val icon: ImageVector? = null
    )

    private val internalOptions = listOf(
        MenuOption("home", "Inicio", Icons.Filled.Home),
        MenuOption("profile", "Perfil", Icons.Filled.Person),
        MenuOption("settings", "Ajustes", Icons.Filled.Settings)
    )

    fun getOptions(): List<MenuOption> = internalOptions

    fun select(
        id: String,
        viewModel: MenuViewModel,
        onNavigate: (String) -> Unit = {}
    ) {
        if (internalOptions.any { it.id == id }) {
            viewModel.selectOption(id)
            onNavigate(id)
        }
    }

    @Composable
    fun NavigationBarContent(
        viewModel: MenuViewModel,
        modifier: Modifier = Modifier,
        onNavigate: (String) -> Unit = {}
    ) {
        val options = remember { internalOptions }
        val selected = viewModel.selectedOption
        NavigationBar(modifier = modifier) {
            options.forEach { option ->
                NavigationBarItem(
                    selected = option.id == selected,
                    onClick = { select(option.id, viewModel, onNavigate) },
                    icon = {
                        option.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = option.label
                            )
                        }
                    },
                    label = { Text(option.label) }
                )
            }
        }
    }

    @Composable
    fun InlineMenuRow(
        viewModel: MenuViewModel,
        modifier: Modifier = Modifier,
        onNavigate: (String) -> Unit = {}
    ) {
        val options = remember { internalOptions }
        val selected = viewModel.selectedOption
        Row(modifier = modifier) {
            options.forEach { option ->
                val isSelected = option.id == selected
                Text(
                    text = if (isSelected) "[${option.label}]" else option.label,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .clickable { select(option.id, viewModel, onNavigate) }
                )
            }
        }
    }
}

