package com.praveen.userfeed.ui.data

import com.praveen.userfeed.ui.data.Status.*

//Helper class to pass Live Objects between Views and View Models
class Response private constructor(val status: Status, val data: kotlin.Any?, val error: Throwable?) {

    companion object {
        fun loading(): Response {
            return Response(LOADING, null, null)
        }

        fun success(data: kotlin.Any?): Response {
            return Response(SUCCESS, data, null)
        }

        fun error(error: Throwable?): Response {
            return Response(ERROR, null, error)
        }

        fun error(data: kotlin.Any?, error: Throwable?): Response {
            return Response(ERROR, data, error)
        }
    }
}
