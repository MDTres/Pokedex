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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
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
                                    uiState.response = RetroFitClient.api.getPokemon(uiState.name)
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

            Column {
                Text(text = "${uiState.response?.name}")
                Text(text = "${uiState.response?.id}")
                Text(
                    text = uiState.response?.types
                        ?.joinToString(", ") { it.type.name }
                        ?: ""
                )
            }

            val imageUrl =
                uiState.response?.sprites?.other?.oficialArtwork?.front_default

            AsyncImage(
                model = imageUrl,
                contentDescription = uiState.response?.name,
                modifier = Modifier.size(200.dp)
            )
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
