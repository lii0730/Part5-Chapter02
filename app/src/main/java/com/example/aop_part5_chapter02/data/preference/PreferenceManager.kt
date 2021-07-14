package com.example.aop_part5_chapter02.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceManager(context: Context) {

	companion object {
		private const val PREFERENCE_NAME = "AOP-PART5-CHAPTER02"
		private const val TOKEN_KEY = "GET_TOKEN_KEY"
		private const val DEFAULT_STRING_VALUE = ""
	}

	private fun getPreferences(context: Context): SharedPreferences {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
	}

	private val prefs by lazy { getPreferences(context) }
	private val editor by lazy { prefs.edit() }

	fun getIdToken() : String? {
		return prefs.getString(TOKEN_KEY, DEFAULT_STRING_VALUE)
	}

	fun setIdToken(token: String) {
		prefs.edit {
			putString(TOKEN_KEY, token)
			commit()
		}
	}

	fun removeToken() {
		prefs.edit {
			putString(TOKEN_KEY, "")
			commit()
		}
	}
}