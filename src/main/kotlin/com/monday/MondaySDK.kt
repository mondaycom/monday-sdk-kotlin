package com.monday

import com.monday.api.MondayApiClient
import com.monday.api.MondayApiResponse
import com.monday.helpers.SingletonHolder

class MondaySDK private constructor(options: Map<String, String>) {
    private val mondayApiClient = MondayApiClient.instance
    private var token = options["token"]

    companion object : SingletonHolder<MondaySDK, Map<String,String>>(::MondaySDK)

    fun api(query: String, options: Map<String, String> = emptyMap()): MondayApiResponse {
        val params = mapOf(
            "query" to query,
            "variables" to options["variables"].orEmpty()
        )

        val token = options.getOrDefault("token", this.token)

        return mondayApiClient.execute(params, token)
    }
}