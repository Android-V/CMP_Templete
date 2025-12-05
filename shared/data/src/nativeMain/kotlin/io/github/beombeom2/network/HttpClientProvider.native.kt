package io.github.beombeom2.network

import io.ktor.client.engine.HttpClientEngine


actual fun provideEngine(): HttpClientEngine =
    Darwin.create()