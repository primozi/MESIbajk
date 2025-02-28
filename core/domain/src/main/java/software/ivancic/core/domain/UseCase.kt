package software.ivancic.core.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class UseCase<in PARAMS, RESULT>(private val coroutineDispatcher: CoroutineDispatcher) {
    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    open suspend operator fun invoke(parameters: PARAMS): Result<RESULT> {
        // Moving all use case's executions to the injected dispatcher
        // In production code, this is usually the Default dispatcher (background thread)
        // In tests, this becomes a TestCoroutineDispatcher
        return withContext(coroutineDispatcher) {
            try {
                execute(parameters).let {
                    Result.success(it)
                }
            } catch (e: Throwable) {
                // TODO add error tracking
                Result.failure(e)
            }
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: PARAMS): RESULT
}
