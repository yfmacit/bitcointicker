package com.yfmacit.cryptocurrencypricetracker.utils

import com.yfmacit.cryptocurrencypricetracker.BuildConfig

class AppLogger {
    companion object {
        fun d(className : Class<*>, message: String?) {
            if (BuildConfig.DEBUG) {
                if (message == null)
                    return

                val TAG = className.simpleName.toString()

                if (message.length > 4000) {
                    val chunkCount = message.length / 4000
                    for (i in 0..chunkCount) {
                        val max = 4000 * (i + 1)
                        if (max >= message.length) {
                            println("$TAG: ${message.substring(4000 * i)}")
                        } else {
                            println("$TAG: ${message.substring(4000 * i, max)}")
                        }
                    }
                } else {
                    println("$TAG: $message")
                }
            }
        }
    }
}