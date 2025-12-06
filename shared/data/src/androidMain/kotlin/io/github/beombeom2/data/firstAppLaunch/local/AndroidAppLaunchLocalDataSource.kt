package io.github.beombeom2.data.firstAppLaunch.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import io.github.beombeom2.data.firstAppLaunch.dataSource.FirstAppLaunchLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.appLaunchDataStore by preferencesDataStore("app_launch_prefs")
private val KEY_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")

class AndroidAppLaunchLocalDataSource(
    private val context: Context
) : FirstAppLaunchLocalDataSource {

    override val isFirstLaunchFlow: Flow<Boolean> =
        context.appLaunchDataStore.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences()) else throw e
            }
            .map { prefs ->
                prefs[KEY_FIRST_LAUNCH] ?: true
            }

    override suspend fun setOnboardingSeen() {
        context.appLaunchDataStore.edit { prefs ->
            prefs[KEY_FIRST_LAUNCH] = false
        }
    }
}