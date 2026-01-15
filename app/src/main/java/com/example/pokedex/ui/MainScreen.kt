package com.example.pokedex.ui

import android.util.Log
import android.widget.ImageView
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
import androidx.compose.material3.ExperimentalMaterial3Api
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    var idPokedex by rememberSaveable { mutableStateOf("") }
    var isShiny by rememberSaveable { mutableStateOf(false) }
    var response by rememberSaveable { mutableStateOf<Pokemon?>(null) }
    val scope = rememberCoroutineScope()





    Scaffold(
        topBar = {
            TopAppBar(
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
                    .background(colorResource(R.color.secundario))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo Rae",
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
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
                        .clip(CircleShape) // botón redondo
                        .background(colorResource(R.color.secundario)) // color del botón
                        .clickable {
                            scope.launch {
                                try {
                                     response = RetroFitClient.api.getPokemon(name)
                                } catch (e: Exception) {
                                    Log.e("API_ERROR", "Error: ${e.message}")
                                }
                            }                    },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Buscar",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }

            Row {
                Text(text = "${response?.name}")
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
