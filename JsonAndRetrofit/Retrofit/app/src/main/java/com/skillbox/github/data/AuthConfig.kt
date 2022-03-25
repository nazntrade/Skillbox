package com.skillbox.github.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user,repo"

    const val CLIENT_ID = "5e207c55a6e7e744f6a4"
    const val CLIENT_SECRET = "2f75ffc3509730ee3f567001be033f23ea6a3efe"
    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"
}