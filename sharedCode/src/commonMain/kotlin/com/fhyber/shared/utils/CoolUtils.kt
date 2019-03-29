package com.fhyber.shared.utils



expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Kotlin Rocks MULTI PLATFORM on ${platformName()}"
}

fun createApplicationScreenMessageTEST() : String {
    return "Kotlin Rocks #1"
}