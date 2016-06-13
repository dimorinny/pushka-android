package ru.nbsp.pushka.network.auth.social

import android.app.Activity
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.util.StringUtils
import javax.inject.Inject

/**
 * Created by Dimorinny on 17.02.16.
 */

class SocialAuthManager(val authListener: SocialAuthListener) : GoogleApiClient.OnConnectionFailedListener {

    init {
        BaseApplication.graph.inject(this)
    }

    companion object {
        val DRIVER_VK = "vk"
        val DRIVER_GOOGLE = "google"
        val GOOGLE_AUTH_REQUEST_CODE = 111
    }

    private var googleSignInOptions: GoogleSignInOptions? = null
    private var googleApiClient: GoogleApiClient? = null

    @Inject
    lateinit var stringUtils: StringUtils

    fun initGoogleAuth(activity: FragmentActivity) {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getGoogleIdToken())
                .requestEmail()
                .requestProfile()
                .build()

        googleApiClient = GoogleApiClient.Builder(activity.applicationContext)
                .enableAutoManage(activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions!!)
                .build()
    }

    private fun getGoogleIdToken(): String {
        return stringUtils.getString(R.string.google_client_server_id)
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        authListener.onSocialLoginError()
    }

    fun login(provider: String, activity: Activity) {
        when (provider) {
            SocialAuthManager.Companion.DRIVER_VK -> VKSdk.login(activity)
            SocialAuthManager.Companion.DRIVER_GOOGLE -> {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
                    val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
                    activity.startActivityForResult(intent, SocialAuthManager.Companion.GOOGLE_AUTH_REQUEST_CODE)
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SocialAuthManager.Companion.GOOGLE_AUTH_REQUEST_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            val account = result.signInAccount

            if (result.isSuccess && account != null && account.idToken != null) {
                authListener.onSocialLoginSuccess(SocialAuthManager.Companion.DRIVER_GOOGLE, account.idToken!!)
            } else {
                authListener.onSocialLoginError()
            }
        }

        var vkData = data
        if (vkData == null) {
            vkData = Intent()
        }

        VKSdk.onActivityResult(requestCode, resultCode, vkData, object : VKCallback<VKAccessToken> {
            override fun onResult(token: VKAccessToken) {
                authListener.onSocialLoginSuccess(SocialAuthManager.Companion.DRIVER_VK, token.accessToken)
            }

            override fun onError(error: VKError) {
                if (error.errorCode != VKError.VK_CANCELED) {
                    authListener.onSocialLoginError()
                }
            }
        })
    }
}