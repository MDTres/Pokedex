package com.example.pokedex.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncFavoritesWorker(
    appContext: Context,
    params: WorkerParameters
): CoroutineWorker(appContext,params){
    override suspend fun doWork(): Result {
        return try {
            val database = AppDatabase.getDatabase(applicationContext)
            val dao = database.pokemonDao()

            val favorites=dao.getAllFavoritesSync()

            if (favorites.isNotEmpty()){
                Log.d("WorkManager", "Sincronizando los ${favorites.size} favoritos que tienes")
            }


            Log.d("WorkManager","Sincronizando favoritos")

            Log.d("WorkManager","Sincronización terminada ")
            Result.success()
        }catch (e: Exception){
            Log.e("WorkManager", "Error sincronizando: ${e.message}")
            Result.retry()
        }
    }
}