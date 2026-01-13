package com.example.pokedex.ui

import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    var idPokedex by rememberSaveable { mutableStateOf("") }
    var isShiny by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokedex")},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ){ innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Row {
                Text("${name}")
                Text("${idPokedex}")


            }
            Switch(onCheckedChange = {isShiny = it}, checked = isShiny )
            ImageView(
                painterResource(),
                ContentDescription = "imagen pokemon"
            )
            Row {
                Text("Tipo:")
                ImageView(
                    painterResource(),
                    ContentDescription = "imagen tipo"


                )
                ImageView(
                    painterResource(),
                    ContentDescription = "imagen tipo 2"


                )
            }
            Row {
                Text("Tipos counter")
                ImageView(
                    painterResource(),
                    ContentDescription = "imagen tipo counter"


                )
                ImageView(
                    painterResource(),
                    ContentDescription = "imagen tipo counter 2"


                )
            }
            Row {
                Text("Tipos mirror")
                ImageView(
                    painterResource(),
                    ContentDescription = "imagen tipo counter"


                )
                ImageView(
                    painterResource(),
                    ContentDescription = "imagen tipo counter 2"


                )
            }




        }


    }
