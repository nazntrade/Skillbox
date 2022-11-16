package com.skillbox.lessons_jsonandretrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonUser = """
            {
                "login": "octocat",
                "id": 1,
                "avatar_url": "https://github.com/images/error/octocat_happy.gif",
            }
        """
        try {
            val jsonObject = JSONObject(jsonUser)
            val login = jsonObject.getString("login")
            val id = jsonObject.getLong("id")
            val avatarUrl = if (jsonObject.has("avatar_url") &&
                jsonObject.isNull("avatar_url").not()
            ) {
                jsonObject.optString("avatar_url")
            } else {
                null
            }
            val user = GithubUser(login, id, avatarUrl)
        } catch (e: JSONException) {}
    }

    data class GithubUser(
        val login: String,
        val id: Long,
        val avatarUrl: String?
    )
}

