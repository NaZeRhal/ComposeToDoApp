package com.maxrzhe.composetodoapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.maxrzhe.composetodoapp.core.util.Constants
import com.maxrzhe.composetodoapp.data.models.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferenceKeys {
        val sortStateKey = stringPreferencesKey(Constants.PREFERENCE_KEY)
    }

    val readSortState: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferenceKeys.sortStateKey] ?: Priority.NONE.name
        }

    suspend fun persistSortState(priority: Priority) {
        context.dataStore.edit { preference ->
            preference[PreferenceKeys.sortStateKey] = priority.name
        }
    }
}