package com.example.demotatraapp

import android.util.Log
import com.example.demotatraapp.data.User
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class MainRepository {
    val client = OkHttpClient()
    fun getUsers(currentPage: Int, perpage: Int, completion: (MutableList<User>) -> Unit): MutableList<User> {
        val url = "https://reqres.in/api/users?page=$currentPage&per_page=$perpage"
        val request = Request.Builder().url(url).build()
        val users = mutableListOf<User>()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONTokener(response.body!!.string()).nextValue() as JSONObject
                val data = jsonObject.getJSONArray("data")

                for (i in 0 until data.length()) {
                    val row = data.getJSONObject(i)

                    users.add(
                        User(
                            row.getInt("id"),
                            row.getString("email"),
                            row.getString("first_name"),
                            row.getString("last_name"),
                            row.getString("avatar")
                        )
                    )
                }
                completion(users)
            }
        })
        return users
    }

    fun getUser(id: Int, completion: (User) -> Unit): User {
        val url = "https://reqres.in/api/users/$id"
        val request = Request.Builder().url(url).build()
        Log.d("Url", url.toString())
        var user = User()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObject = JSONTokener(response.body!!.string()).nextValue() as JSONObject
                val data = jsonObject.getJSONObject("data")
                Log.d("data user", data.toString())
                user.avatar = data.getString("avatar")
                user.email = data.getString("email")
                user.first_name = data.getString("first_name")
                user.last_name = data.getString("last_name")
                completion(user)
            }
        })
        return user
    }

}