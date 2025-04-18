package com.mangaapp.manga.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Manages session data using DataStore for persisting data.
 */
class SessionManagerDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val LOGIN_KEY = booleanPreferencesKey("logged_in")

    /**
     * Saves the login state (whether the user is logged in or not) to the DataStore.
     */
    suspend fun saveLoginState(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGIN_KEY] = isLoggedIn
        }
    }

    /**
     * Retrieves the current login state from the DataStore.
     */
    suspend fun getLoginState(): Boolean = dataStore.data.map { preferences ->
        preferences[LOGIN_KEY] == true
    }.first()

    /**
     * Clears all data from the DataStore.
     */
    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}