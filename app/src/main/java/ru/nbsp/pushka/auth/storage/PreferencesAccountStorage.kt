package ru.nbsp.pushka.auth.storage

import android.content.SharedPreferences
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import ru.nbsp.pushka.auth.Account

/**
 * Created by Dimorinny on 20.02.16.
 */

class PreferencesAccountStorage(
        val gson: Gson,
        val sharedPreferences: SharedPreferences) : AccountStorageRepository {

    companion object {
        private const val PREF_ACCOUNT_KEY = "pref_account_key"
    }

    override fun getAccount(): Account? {
        val accountString = sharedPreferences.getString(PREF_ACCOUNT_KEY, null)
        return if (accountString != null) gson.fromJson<Account>(accountString) else null
    }

    override fun saveAccount(account: Account) {
        sharedPreferences.edit().putString(PREF_ACCOUNT_KEY, gson.toJson(account)).commit()
    }

    override fun clear() {
        sharedPreferences.edit().remove(PREF_ACCOUNT_KEY).commit()
    }
}