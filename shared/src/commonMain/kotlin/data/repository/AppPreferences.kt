package data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent


data class AppPreferences(
    val token: String = "",
)

class AppPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : KoinComponent {

    private val logger = Logger.withTag("UserPreferencesManager")

    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("USER_TOKEN")
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    /**
     * Use this if you don't want to observe a flow.
     */
    suspend fun fetchInitialPreferences() =
        mapAppPreferences(dataStore.data.first().toPreferences())

    /**
     * Get the user preferences flow. When it's collected, keys are mapped to the
     * [UserPreferences] data class.
     */
    val userPreferencesFlow: Flow<AppPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                logger.d { "Error reading preferences: $exception" }
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapAppPreferences(preferences)
        }


    /**
     * Sets the userId that we get from the Ktor API (on button click).
     */
    suspend fun setUserToken(userToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_TOKEN] = userToken
        }
    }


    /**
     * Get the preferences key, then map it to the data class.
     */
    private fun mapAppPreferences(preferences: Preferences): AppPreferences {
        val userToken = preferences[PreferencesKeys.USER_TOKEN] ?: ""
        Logger.d { "lastScreen: $userToken" }
        return AppPreferences(userToken)
    }
}