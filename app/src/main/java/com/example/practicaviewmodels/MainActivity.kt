package com.example.practicaviewmodels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practicaviewmodels.ui.screens.AppScreen
import com.example.practicaviewmodels.ui.screens.MenuBar
import com.example.practicaviewmodels.ui.screens.VistaRecetas
import com.example.practicaviewmodels.ui.screens.AñadirRecetas
import com.example.practicaviewmodels.ui.screens.BuscarRecetaScreen
import com.example.practicaviewmodels.ui.screens.DescripcionRecetaScreen
import com.example.practicaviewmodels.ui.viewmodels.RecipeViewModel

class MainActivity : ComponentActivity() {
    private val recipeViewModel: RecipeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { AppRoot(recipeViewModel) }
        }
    }
}

@Composable
private fun AppRoot(viewModel: RecipeViewModel) {
    val nav = rememberNavController()
    val backEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backEntry?.destination?.route?.substringBefore("/")
    val currentScreen = when (currentRoute) {
        "inicio" -> AppScreen.Inicio
        "recetas" -> AppScreen.VerRecetas
        "agregar" -> AppScreen.AgregarReceta
        "buscar" -> AppScreen.Buscar
        "detalle" -> AppScreen.VerRecetas
        else -> AppScreen.Inicio
    }

    Scaffold { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            MenuBar(
                currentScreen = currentScreen,
                onInicio = { nav.navigateSingleTop("inicio") },
                onVerRecetas = { nav.navigateSingleTop("recetas") },
                onAgregarReceta = { nav.navigateSingleTop("agregar") },
                onBuscarReceta = { nav.navigateSingleTop("buscar") }
            )
            NavHost(
                navController = nav,
                startDestination = "inicio",
                modifier = Modifier.weight(1f)
            ) {
                composable("inicio") {
                    InicioAcciones(
                        onVerRecetas = { nav.navigateSingleTop("recetas") },
                        onAgregar = { nav.navigateSingleTop("agregar") },
                        onBuscar = { nav.navigateSingleTop("buscar") }
                    )
                }
                composable("recetas") {
                    val recetas by viewModel.recetas.collectAsState()
                    VistaRecetas(
                        recetas = recetas,
                        onRecetaClick = { r -> nav.navigate("detalle/${r.id}") }
                    )
                }
                composable("agregar") {
                    AñadirRecetas(
                        onBack = { nav.popBackStack() },
                        onGuardar = { receta ->
                            viewModel.agregar(
                                receta.titulo,
                                receta.ingredientes,
                                receta.pasos
                            )
                            nav.navigateSingleTop("inicio")
                        }
                    )
                }
                composable("buscar") {
                    BuscarRecetaScreen(
                        onBack = { nav.popBackStack() },
                        onSearch = {
                            nav.navigateSingleTop("recetas")
                        }
                    )
                }
                composable(
                    route = "detalle/{id}",
                    arguments = listOf(navArgument("id") { defaultValue = "" })
                ) { stack ->
                    val id = stack.arguments?.getString("id")
                    val recetas by viewModel.recetas.collectAsState()
                    val receta = recetas.firstOrNull { it.id == id }
                    if (receta != null) {
                        DescripcionRecetaScreen(
                            receta = receta,
                            onBack = { nav.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

private fun NavHostController.navigateSingleTop(route: String) {
    if (currentDestination?.route?.substringBefore("/") == route) return
    navigate(route)
}

@Composable
private fun InicioAcciones(
    onVerRecetas: () -> Unit,
    onAgregar: () -> Unit,
    onBuscar: () -> Unit
) {
    val acciones = listOf(
        AccionItem("Ver Recetas") { onVerRecetas() },
        AccionItem("Añadir Receta") { onAgregar() },
        AccionItem("Buscar Receta") { onBuscar() }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Acciones",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        Spacer(Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(acciones) { item ->
                BoxAccion(titulo = item.titulo, onClick = item.onClick)
            }
        }
    }
}

private data class AccionItem(
    val titulo: String,
    val onClick: () -> Unit
)

@Composable
private fun BoxAccion(
    titulo: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black)
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = titulo,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}
