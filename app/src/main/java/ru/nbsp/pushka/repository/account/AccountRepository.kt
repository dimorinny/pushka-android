package ru.nbsp.pushka.repository.account

import ru.nbsp.pushka.auth.Account

/**
 * Created by Dimorinny on 20.02.16.
 */
interface AccountRepository {
    fun getAccount(): Account?
    fun saveAccount(account: Account)
    fun clear()
}