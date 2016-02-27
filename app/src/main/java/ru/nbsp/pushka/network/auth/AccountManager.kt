package ru.nbsp.pushka.network.auth

import ru.nbsp.pushka.repository.account.AccountRepository
import ru.nbsp.pushka.util.TimestampUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimorinny on 20.02.16.
 */
@Singleton
class AccountManager
    @Inject constructor(
            val accountRepository: AccountRepository,
            val timestampUtils: TimestampUtils) {

    fun setAccount(account: Account) {
        accountRepository.saveAccount(account)
    }

    fun getAccount(): Account? {
        return accountRepository.getAccount()
    }

    fun clear() {
        accountRepository.clear()
    }

    fun isAccountValid(): Boolean {
        val account = accountRepository.getAccount() ?: return false
        return timestampUtils.afterNow(account.expiredTimestamp)
    }
}