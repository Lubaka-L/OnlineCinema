package com.example.onlinecinema.core
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Response

object ResponseExtension {

    /**
     * Обрабатывает [retrofit2.Response] и возвращает ResultWrapper
     */
    fun <T : Any> Response<T>.handleResponse(isOverflowException: MutableStateFlow<Boolean>): ResultWrapper<T> {
        return try {
            if (this.isSuccessful) {
                return this.body()?.let { body ->
                    ResultWrapper.success(body)
                } ?: ResultWrapper.error(
                    error = "Empty Body received"
                )
            } else {
                Log.i("ResponseExtension", "${code()}")
                when (code()) {
                    402 -> {
                        Log.i("ResponseExtension", "Exception 402")
                        isOverflowException.update { true }
                        ResultWrapper.error(
                            error = "No error body\n",
                            errorType = HttpExceptions.RequestsOverflow
                        )
                    }
                    else -> {
                        ResultWrapper.error(
                            error = "No error body\n"
                        )
                    }
                }
            }
        } catch (exception: Exception) {
            ResultWrapper.error(
                error = "$exception\n"
            )
        }
    }

    suspend fun <T : Any> returnSafely(
        scope: CoroutineDispatcher = Dispatchers.IO,
        content: suspend CoroutineDispatcher.() -> ResultWrapper<T>
    ): ResultWrapper<T> {
        return try {
            content.invoke(scope)
        } catch (exception: Exception) {
            ResultWrapper.error(error = exception.message.toString())
        }
    }
}