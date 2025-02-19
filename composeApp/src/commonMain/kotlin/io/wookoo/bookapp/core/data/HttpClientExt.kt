package io.wookoo.bookapp.core.data

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.wookoo.bookapp.core.domain.DataError
import io.wookoo.bookapp.core.domain.AppResult
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse)
        : AppResult<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return AppResult.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return AppResult.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return AppResult.Error(DataError.Remote.UNKNOWN)
    }
    return responseToResult(response)

}


suspend inline fun <reified T> responseToResult(
    response: HttpResponse,
): AppResult<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                AppResult.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                AppResult.Error(DataError.Remote.SERIALIZATION)
            }
        }

        408 -> AppResult.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> AppResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> AppResult.Error(DataError.Remote.SERVER)
        else -> AppResult.Error(DataError.Remote.UNKNOWN)
    }
}