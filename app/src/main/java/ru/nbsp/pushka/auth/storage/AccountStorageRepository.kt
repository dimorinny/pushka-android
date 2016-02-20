package ru.nbsp.pushka.auth.storage

import ru.nbsp.pushka.auth.Account

/**
 * Created by Dimorinny on 20.02.16.
 */
interface AccountStorageRepository {
    fun getAccount(): Account?
    fun saveAccount(account: Account)
    fun clear()
}