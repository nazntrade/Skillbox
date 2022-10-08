package com.skillbox.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
    context: Context
) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = DATASTORE_NAME)

    suspend fun save(text: String) {
        dataStore.edit {
            it[KEY] = text
        }
    }

    fun observe(): Flow<String> {
        return dataStore.data
            .map {
                it[KEY].orEmpty()
            }
    }

    companion object {
        private const val DATASTORE_NAME = "datastore"
        private val KEY = preferencesKey<String>("key")
    }

}