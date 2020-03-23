package com.monday.api

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.monday.Constants.Companion.MONDAY_API_URL
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class MondayApiClient private constructor() {
    private val client = OkHttpClient()
    private val gson = Gson()


    companion object {
        @JvmStatic
        val instance: MondayApiClient by lazy {
            MondayApiClient()
        }

        private const val COULD_NOT_PARSE_JSON_RESPONSE_ERROR =
            "Could not parse JSON from monday.com's GraphQL API response";
        private const val TOKEN_IS_REQUIRED_ERROR = "Token is required";
    }

    fun execute(data: Map<String, String>, token: String?, options: Map<String, String> = emptyMap()): MondayApiResponse {
        if (token.isNullOrEmpty()) {
            throw Exception(TOKEN_IS_REQUIRED_ERROR)
        }

        val url = options["url"] ?: MONDAY_API_URL
        val path = options["path"].orEmpty()
        val fullUrl = "$url/$path"
        return apiRequest(fullUrl, data, token, options)
    }

    private fun apiRequest(url: String, data: Map<String, String>, token: String, options: Map<String, String>): MondayApiResponse {
        val body = gson.toJson(data)
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", token)
            .addHeader("Content-Type", "application/json")
            .method(options["method"]?: "POST", body.toRequestBody())
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            gson.fromJson<MondayApiResponse>(response.body!!.string(), MondayApiResponse::class.java)
        } else {
            MondayApiResponse(
                data = null,
                errors = JsonObject().apply {
                    addProperty("message", COULD_NOT_PARSE_JSON_RESPONSE_ERROR)
                }
            )
        }
    }
}

data class MondayApiResponse(
    @SerializedName("data")
    val data: JsonElement?,
    @SerializedName("errors")
    val errors: JsonElement?
)