package io.github.beombeom2.cmp

class DesktopPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = DesktopPlatform()
