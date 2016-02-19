package ru.nbsp.pushka.auth.social

/**
 * Created by Dimorinny on 17.02.16.
 */
interface SocialAuthListener {
    fun onSocialLoginSuccess(provider: String, token: String)
    fun onSocialLoginError()
}