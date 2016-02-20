package ru.nbsp.pushka.auth

import ru.nbsp.pushka.auth.storage.AccountStorageRepository
import ru.nbsp.pushka.util.TimestampUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 20.02.16.
 */
@Singleton
class AccountManager
    @Inject constructor(
            val accountStorage: AccountStorageRepository,
            val timestampUtils: TimestampUtils){

    fun setAccount(account: Account) {
        accountStorage.saveAccount(account)
    }

    fun getAccount(): Account? {
        return accountStorage.getAccount()
    }

    fun isValid(): Boolean {
        val account = accountStorage.getAccount() ?: return false
        return timestampUtils.afterNow(account.expiredTimestamp)
    }
}