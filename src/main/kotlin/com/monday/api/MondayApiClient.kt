package com.monday.api

import com.google.gson.Gson
import com.monday.Constants.Companion.MONDAY_API_URL
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class MondayApiClient private constructor() {
    private val client = OkHttpClient()
    private val gson = Gson()

    companion object {
        private var instance: MondayApiClient? = null
        fun getInstance(): MondayApiClient {
            if (instance == null) {
                instance = MondayApiClient()
            }

            return instance!!
        }

        private const val COULD_NOT_PARSE_JSON_RESPONSE_ERROR =
            "Could not parse JSON from monday.com's GraphQL API response";
        private const val TOKEN_IS_REQUIRED_ERROR = "Token is required";
    }

    fun execute(data: Map<String, String>, token: String?, options: Map<String, String> = emptyMap()): Response {
        if (token.isNullOrEmpty()) {
            throw Exception(TOKEN_IS_REQUIRED_ERROR)
        }

        val url = options["url"] ?: MONDAY_API_URL
        val path = options["path"].orEmpty()
        val fullUrl = "$url/$path"
        return apiRequest(fullUrl, data, token, options)
    }

    private fun apiRequest(url: String, data: Map<String, String>, token: String, options: Map<String, String>): Response {
        val body = gson.toJson(data)
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", token)
            .addHeader("Content-Type", "application/json")
            .method(options["method"]?: "POST", body.toRequestBody())
            .build()

        return client.newCall(request).execute()
    }
}