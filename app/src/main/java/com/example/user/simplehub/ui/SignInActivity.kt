package com.example.user.simplehub.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.user.simplehub.R
import com.example.user.simplehub.api.authApi
import com.example.user.simplehub.api.updateToken
import com.example.user.simplehub.utils.enqueue
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.app_bar.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SignInActivity : AppCompatActivity() {

    companion object {
        val TAG = SignInActivity::class.java.simpleName
        const val CLIENT_ID = "d3be1425f2b68349c8cf"
        const val CLIENT_SECRET = "8f604dacb00a15456bd8854fe003874d33287c30"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = null

        signinButton.setOnClickListener {
            val authUri = Uri.Builder().scheme("https")
                    .authority("github.com")
                    .appendPath("login")
                    .appendPath("oauth")
                    .appendPath("authorize")
                    .appendQueryParameter("client_id", CLIENT_ID)
                    .appendQueryParameter("scope", "repo")
                    .appendQueryParameter("scope", "user:follow")
                    .build()

            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(this, authUri)

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        check(intent != null)
        check(intent?.data != null)

        val uri = intent?.data
        val code = uri?.getQueryParameter("code") ?: throw IllegalStateException("no code!")

        getAccessToken(code)

    }

    private fun getAccessToken(code: String) {
        val call = authApi.getAccessToken(CLIENT_ID, CLIENT_SECRET, code)

        call.enqueue({
            it.body()?.let {
                Log.i(TAG, "code = ${it.accessToken}")
                updateToken(this, it.accessToken)

                startActivity<ProfileActivity>()
            }
        }, {

        })
    }
}

