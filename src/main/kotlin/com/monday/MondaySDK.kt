package com.monday

import com.monday.api.MondayApiClient
import okhttp3.Response

class MondaySDK private constructor(options: Map<String, String>) {
    private val mondayApiClient = MondayApiClient.getInstance()
    private var token = options["token"]

    companion object {
        private var instance: MondaySDK? = null
        fun getInstance(options: Map<String, String> = emptyMap()): MondaySDK {
            if (instance == null) {
                instance =
                    MondaySDK(options)
            }

            return instance!!
        }
    }

    fun api(query: String, options: Map<String, String> = emptyMap()): Response {
        val params = mapOf(
            "query" to query,
            "variables" to options["variables"].orEmpty()
        )

        val token = options.getOrDefault("token", this.token)

        return mondayApiClient.execute(params, token)
    }
}