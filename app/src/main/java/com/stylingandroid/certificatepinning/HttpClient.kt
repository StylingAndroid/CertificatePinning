package com.stylingandroid.certificatepinning

import android.os.Build
import okhttp3.Call
import okhttp3.Callback
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import okhttp3.logging.HttpLoggingInterceptor.Logger
import java.io.IOException

class HttpClient(
        private val okHttpClient: OkHttpClient = createOkHttpClient()
) {

    fun load(display: (String) -> Unit) {
        val request = Request.Builder()
                .url("https://blog.stylingandroid.com")
                .head()
                .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                display(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                display("request: $request \nResponse Code: ${response.code()} Protocol: ${response.protocol()}")
            }
        })
    }
}

private const val DOMAIN = "*.stylingandroid.com"
private const val PIN = "sha256/htJkaSJB+j8Ckv7ovGieQJYqyV/M4K7YRt4je18A7T4="
private const val BACKUP = "sha256/x9SZw6TwIqfmvrLZ/kz1o0Ossjmn728BnBKpUFqGNVM="

private fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor(Logger { println(it) }).setLevel(Level.BASIC))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            certificatePinner(CertificatePinner.Builder()
                    .add(DOMAIN, PIN)
                    .add(DOMAIN, BACKUP)
                    .build()
            )
        }
    }.build()
}
