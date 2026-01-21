package com.example.pokedex.ui

import android.util.Log
import coil.compose.AsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedex.R
import com.example.pokedex.data.Pokemon
import com.example.pokedex.data.RetroFitClient
import com.example.pokedex.ui.theme.PokedexBlue
import com.example.pokedex.ui.theme.PokedexRed
import com.example.pokedex.ui.theme.PokedexYellow
import com.example.pokedex.ui.theme.Primario
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import androidx.compose.runtime.collectAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val viewModel: ViewModlePoke = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )

    val uiState by viewModel.uiState
    val isBottom by viewModel.isTopBarBottom.collectAsState()

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            if (!isBottom) {
                TopBar(
                    uiState = uiState,
                    onSearchClick = {
                        scope.launch {
                            try {
                                val result =
                                    RetroFitClient.api.getPokemon(uiState.name.lowercase())
                                viewModel.updateResponse(result)
                            } catch (e: Exception) {
                                Log.e("API_ERROR", "Error: ${e.message}")
                            }
                        }
                    },
                    onPokeballClick = { viewModel.onPokeballClick() },
                    onNameChange = { viewModel.onNameChange(it) }
                )
            }
        },
        bottomBar = {
            if (isBottom) {
                TopBar(
                    uiState = uiState,
                    onSearchClick = {
                        scope.launch {
                            try {
                                val result =
                                    RetroFitClient.api.getPokemon(uiState.name.lowercase())
                                viewModel.updateResponse(result)
                            } catch (e: Exception) {
                                Log.e("API_ERROR", "Error: ${e.message}")
                            }
                        }
                    },
                    onPokeballClick = { viewModel.onPokeballClick() },
                    onNameChange = { viewModel.onNameChange(it) }
                )
            }
        },
        modifier = Modifier.systemBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(Primario)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {

                    // NOMBRE
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
                                .height(50.dp)
                                .background(Color(0xFFD32F2F), shape = RoundedCornerShape(10.dp))
                                .padding(horizontal = 24.dp, vertical = 12.dp),
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

                    Spacer(modifier = Modifier.width(20.dp))

                    // ID
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
                                .height(50.dp)
                                .background(
                                    Color(0xFFD32F2F),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 24.dp, vertical = 12.dp),
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

                    Spacer(modifier = Modifier.width(20.dp))

                    // FAV
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Fav:",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    viewModel.toggleFav()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = if (uiState.isFav) R.drawable.favsi else R.drawable.favno,
                                contentDescription = uiState.response?.name,
                                modifier = Modifier
                                    .size(45.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                val imageUrl =
                    uiState.response?.sprites?.other?.oficialArtwork?.front_default

                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .background(Color(0xFF6B9B46), shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = uiState.response?.name,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Tipos:",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        uiState.response?.types?.forEach { typeItem ->
                            Image(
                                painter = painterResource(
                                    id = viewModel.getTypeImageByName(typeItem.type.name)
                                ),
                                contentDescription = typeItem.type.name,
                                modifier = Modifier
                                    .size(75.dp)
                                    .padding(horizontal = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TopBar(
    uiState: UIState,
    onSearchClick: () -> Unit,
    onPokeballClick: () -> Unit,
    onNameChange: (String) -> Unit
) {
    // Usamos Surface para el fondo y la elevación (sombra)
    Surface(
        color = PokedexBlue,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // Se ajustará al contenido (aprox los 140dp)
            .padding(bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TÍTULO
            Text(
                text = "Pokedex",
                fontFamily = FontFamily(Font(R.font.pokemonfont)),
                color = PokedexYellow,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // FILA DE BÚSQUEDA
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { onPokeballClick() },
                    painter = painterResource(id = R.drawable.pokebola_pokeball_png_0),
                    contentDescription = "Logo"
                )

                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { onNameChange(it) },
                    placeholder = { Text("Escribe nombre o ID", fontSize = 14.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                        .heightIn(min = 50.dp, max = 60.dp), // Controlamos altura del campo
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Color.Black
                    )
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(PokedexRed)
                        .clickable { onSearchClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

