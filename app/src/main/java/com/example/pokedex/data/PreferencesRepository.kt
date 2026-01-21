package com.example.pokedex.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepository(context: Context) {

    private val dataStore = context.dataStore

    private val TOP_BAR_AT_BOTTOM = booleanPreferencesKey("top_bar_at_bottom")

    val isTopBarBottom: Flow<Boolean> = dataStore.data
        .map { prefs ->
            prefs[TOP_BAR_AT_BOTTOM] ?: false
        }

    suspend fun toggleTopBar() {
        dataStore.edit { prefs ->
            val current = prefs[TOP_BAR_AT_BOTTOM] ?: false
            prefs[TOP_BAR_AT_BOTTOM] = !current
        }
    }
}
