package com.monday

class Constants {
    companion object {
        const val MONDAY_PROTOCOL = "https"
        const val MONDAY_DOMAIN = "monday.com"
        const val MONDAY_API_URL = "$MONDAY_PROTOCOL://api.$MONDAY_DOMAIN/v2"
        const val MONDAY_OAUTH_URL = "$MONDAY_PROTOCOL://auth.$MONDAY_DOMAIN/oauth2/authorize"
        const val MONDAY_OAUTH_TOKEN_URL = "$MONDAY_PROTOCOL://auth.$MONDAY_DOMAIN/oauth2/token"
    }
}
