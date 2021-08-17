package com.example.myapplication2

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

val TAG = "HTTP Test"


class httpAPI() {
    interface OnRequestListener {
        fun onSuccess(result: Any)
        fun onError()
    }
    var client: OkHttpClient? = null
    var request: Request? = null
    val builder = FormBody.Builder()

    init {
        builder.add("",  "")
        val formBody = builder.build()
        client = OkHttpClient()
        request = Request.Builder()
            .method("POST", formBody)
            .url("https://asia-east2-ductech-cms.cloudfunctions.net/GetAllProjectName")
            .build()
    }

    fun postAPI() {
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "onResponse: " + response)
            }
        })
    }

    public var onRequestListener : OnRequestListener? = null

    fun getUserMail(name: String, number: String, listener: OnRequestListener) {

        this.onRequestListener = listener

        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()
                val itemList = JSONObject(responseStr)

                onRequestListener?.onSuccess(itemList)
            }
        })
    }

}