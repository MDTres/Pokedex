package com.example.pokedex.ui

import android.util.Log
import android.widget.ImageView
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedex.R
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.ui.theme.PokedexYellow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.data.Pokemon
import com.example.pokedex.data.RetroFitClient
import com.example.pokedex.ui.theme.PokedexBlue
import com.example.pokedex.ui.theme.PokedexRed
import com.example.pokedex.ui.theme.Primario
import kotlinx.coroutines.launch

import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModle: ViewModlePoke = viewModel(),
) {
    val uiState by viewModle.uiState

    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                    text = "Pokedex",

                    fontFamily = FontFamily(Font(R.font.pokemonfont)),
                    color = PokedexYellow,
                    fontSize = 34.sp
                    ) },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = PokedexBlue
                        )
            )
        },
        modifier = Modifier.systemBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).
            background(Primario)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.pokebola_pokeball_png_0),
                    contentDescription = "Logo Rae",
                )
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { viewModle.onNameChange(it) },
                    placeholder = { Text("Palabra") },
                    singleLine = true,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        Color.Black
                    )
                )
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)          // botón redondo
                        .background(PokedexRed)     // color del botón
                        .clickable {                // clic dentro del Modifier
                            scope.launch {
                                try {
                                    val result = RetroFitClient.api.getPokemon(uiState.name.lowercase())
                                    // Llamamos a la función del ViewModel en lugar de reasignar aquí
                                    viewModle.updateResponse(result)
                                } catch (e: Exception) {
                                    Log.e("API_ERROR", "Error: ${e.message}")
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search, // lupa
                        contentDescription = "Buscar",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }

            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally // Centra la Row horizontalmente
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // Para que el Row ocupe todo el ancho
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center, // Centra los dos bloques en la pantalla
                    verticalAlignment = Alignment.Bottom // Alinea los boxes por la base
                ) {
                    // Bloque para el NOMBRE
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Nombre:",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .widthIn(min = 160.dp)
                                .height(50.dp)// Ancho mínimo para que el box sea más grande
                                .background(Color(0xFFD32F2F), shape = RoundedCornerShape(10.dp))
                                .padding(horizontal = 24.dp, vertical = 12.dp), // Más padding = Box más grande
                            contentAlignment = Alignment.Center
                        ) {
                            uiState.response?.let { data ->
                                Text(
                                    text = data.name.replaceFirstChar { it.uppercase() },
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp)) // Espacio entre Nombre e ID

                    // Bloque para el ID
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "ID:",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .widthIn(min = 100.dp)
                                .height(50.dp)// Ancho mínimo específico para el ID
                                .background(Color(0xFFD32F2F),
                                    shape = RoundedCornerShape(10.dp))
                                .padding(horizontal = 24.dp, vertical = 12.dp), // Mismo padding para consistencia
                            contentAlignment = Alignment.Center
                        ) {
                            uiState.response?.let { data ->
                                Text(
                                    text = "#${data.id}",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }


                val imageUrl =
                    uiState.response?.sprites?.other?.oficialArtwork?.front_default

                Box(
                    modifier = Modifier
                        .size(350.dp)           // tamaño del Box
                        .background(Color(0xFF6B9B46), shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 24.dp, vertical = 12.dp),// margen externo
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = uiState.response?.name,
                        modifier = Modifier
                            .fillMaxSize() // la imagen ocupa todo el Box
                    )
                }


                Text(
                    text = uiState.response?.types
                        ?.joinToString(", ") { it.type.name }
                        ?: ""
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {
        MainScreen()
    }
}
