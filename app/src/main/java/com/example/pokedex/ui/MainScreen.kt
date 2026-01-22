package com.example.pokedex.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.data.RetroFitClient
import com.example.pokedex.ui.theme.PokedexBlue
import com.example.pokedex.ui.theme.PokedexRed
import com.example.pokedex.ui.theme.PokedexYellow
import com.example.pokedex.ui.theme.Primario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: ViewModlePoke = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )

    val uiState by viewModel.uiState
    val isBottom by viewModel.isTopBarBottom.collectAsState()


    val favoriteList by viewModel.pokemonDao.getAllFavorites().collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            if (!isBottom) {
                TopBar(
                    uiState = uiState,
                    onSearchClick = {
                        scope.launch {
                            try {
                                val result = RetroFitClient.api.getPokemon(uiState.name.lowercase())
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
                                val result = RetroFitClient.api.getPokemon(uiState.name.lowercase())
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

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .background(Primario)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // NOMBRE
                    Column(horizontalAlignment = Alignment.Start) {
                        Text("Nombre:", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .widthIn(min = 160.dp)
                                .height(50.dp)
                                .background(Color(0xFFD32F2F), shape = RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            uiState.response?.let { data ->
                                Text(data.name.replaceFirstChar { it.uppercase() }, color = Color.White, fontSize = 18.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    // ID
                    Column(horizontalAlignment = Alignment.Start) {
                        Text("ID:", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .widthIn(min = 80.dp)
                                .height(50.dp)
                                .background(Color(0xFFD32F2F), shape = RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            uiState.response?.let { data ->
                                Text("#${data.id}", color = Color.White, fontSize = 18.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    // FAV (CORAZÓN)
                    Column(horizontalAlignment = Alignment.Start) {
                        Text("Fav:", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier.size(50.dp).clickable { viewModel.toggleFav() },
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = if (uiState.isFav) R.drawable.favsi else R.drawable.favno,
                                contentDescription = "Favorite Icon",
                                modifier = Modifier.size(45.dp)
                            )
                        }
                    }
                }

                // IMAGEN POKEMON
                val imageUrl = uiState.response?.sprites?.other?.oficialArtwork?.front_default
                Box(
                    modifier = Modifier
                        .size(320.dp)
                        .background(Color(0xFF6B9B46), shape = RoundedCornerShape(10.dp))
                        .padding(16.dp),
                ) {
                    AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.fillMaxSize())
                }

                // TIPOS
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("Tipos:", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.Center) {
                        uiState.response?.types?.forEach { typeItem ->
                            Image(
                                painter = painterResource(id = viewModel.getTypeImageByName(typeItem.type.name)),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp).padding(4.dp)
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                    color = Color.White.copy(alpha = 0.4f)
                )
            }


            item {
                Text(
                    text = "LISTA DE FAVORITOS",
                    color = PokedexYellow,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }


            if (favoriteList.isEmpty()) {
                item {
                    Text("No hay favoritos guardados", color = Color.White.copy(alpha = 0.5f), modifier = Modifier.padding(16.dp))
                }
            } else {
                items(favoriteList) { poke ->
                    FavoriteCard(id = poke.id, name = poke.name)
                }
            }


            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}

@Composable
fun FavoriteCard(id: Int, name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC62828)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "#$id", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = name.uppercase(), color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
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
    Surface(
        color = PokedexBlue,
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pokedex",
                fontFamily = FontFamily(Font(R.font.pokemonfont)),
                color = PokedexYellow,
                fontSize = 45.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(45.dp).clickable { onPokeballClick() },
                    painter = painterResource(id = R.drawable.pokebola_pokeball_png_0),
                    contentDescription = "Config"
                )
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { onNameChange(it) },
                    placeholder = { Text("Buscar...", fontSize = 14.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(horizontal = 8.dp).weight(1f).height(55.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
                Box(
                    modifier = Modifier.size(50.dp).clip(CircleShape).background(PokedexRed).clickable { onSearchClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}
