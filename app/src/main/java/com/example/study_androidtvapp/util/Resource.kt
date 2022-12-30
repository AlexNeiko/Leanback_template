package com.example.study_androidtvapp.util

/** State class (Sealed) of network responses */
sealed class Resource<T> {
    class Idle<T> : Resource<T>() /** not used. Empty inside */
    class Loading<T> : Resource<T>() /** now is loading */
    data class Success<T>( /** data after load */
        val data: T,
        val message: String? = null,
    ) : Resource<T>()

    data class Error<T>( /** load error */
        val errorData: String
    ) : Resource<T>()
}