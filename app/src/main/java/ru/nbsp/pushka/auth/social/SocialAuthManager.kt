package ru.nbsp.pushka.auth.social

import android.app.Activity
import android.content.Intent
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError

/**
 * Created by Dimorinny on 17.02.16.
 */

class SocialAuthManager(val authListener: SocialAuthListener) {

    companion object {
        val DRIVER_VK = "vk"
        val DRIVER_GOOGLE = "google"
    }

    fun login(provider: String, activity: Activity) {
        when (provider) {
            DRIVER_VK -> VKSdk.login(activity)
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var vkData = data

        if (resultCode == Activity.RESULT_OK) {
            vkData = Intent()
        }

        VKSdk.onActivityResult(requestCode, resultCode, vkData!!, object : VKCallback<VKAccessToken> {
            override fun onResult(token: VKAccessToken) {
                authListener.onSocialLoginSuccess(DRIVER_VK, token.accessToken)
            }

            override fun onError(error: VKError) {
                authListener.onSocialLoginError()
            }
        })




    }
}