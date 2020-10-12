package com.monday.graphql

abstract class Value<T>(val value: T) {
    abstract fun asString(): String
}