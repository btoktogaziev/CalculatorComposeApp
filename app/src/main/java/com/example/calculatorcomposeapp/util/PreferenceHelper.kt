package com.example.calculatorcomposeapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    var chosenTheme: String
        get() = sharedPreferences.getString("theme", "System") ?: "System"
        set(value) = sharedPreferences.edit { putString("theme", value) }
}