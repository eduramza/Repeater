package com.ramattec.data.local

import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val PREF_TAG = "repeater_pref_tag"
const val PREF_NAME = "pref_name"

@Singleton
class MyPreferenceManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStoredName(): String {
        return prefs.getString(PREF_NAME, "") ?: ""
    }
    fun setStoredName(query: String) {
        prefs.edit().putString(PREF_NAME, query).apply()
    }
}