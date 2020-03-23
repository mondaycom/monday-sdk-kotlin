package com.monday

import com.monday.api.MondayApiClient
import com.monday.api.MondayApiResponse

class MondaySDK private constructor(options: Map<String, String>) {
    private val mondayApiClient = MondayApiClient.instance
    private var token = options["token"]

    companion object {
        private val lock = Any()
        private var instance: MondaySDK? = null
        @JvmStatic
        @JvmOverloads
        fun getInstance(options: Map<String, String> = emptyMap()): MondaySDK {
            synchronized(lock){
                if (instance == null) {
                    synchronized(lock) {
                        if(instance == null)
                            instance = MondaySDK(options)
                    }
                }
            }
            return instance!!
        }
    }

    @JvmOverloads
    fun api(query: String, options: Map<String, String> = emptyMap()): MondayApiResponse {
        val params = mapOf(
            "query" to query,
            "variables" to options["variables"].orEmpty()
        )

        val token = options.getOrDefault("token", this.token)

        return mondayApiClient.execute(params, token)
    }
}