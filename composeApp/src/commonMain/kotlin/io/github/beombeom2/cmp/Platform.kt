package io.github.beombeom2.cmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform