package com.ingresse.sdk.url.builder

enum class Environment(val prefix: String) {
    PROD(""),
    HML("hml-"),
    TEST("test-"),
    STG("stg-")
}