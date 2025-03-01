package software.ivancic.bikes.domain

import org.koin.core.annotation.Factory
import software.ivancic.bikes.domain.model.Bike
import software.ivancic.core.domain.CoroutineDispatchers
import software.ivancic.core.domain.UseCase

@Factory
class GetListOfBikesWithAvailabilityDataUseCase(
    private val bikesRepository: BikesRepository,
    coroutineDispatchers: CoroutineDispatchers,
) : UseCase<Unit, List<Bike>>(coroutineDispatchers.io) {
    override suspend fun execute(parameters: Unit): List<Bike> {
        return try {
            bikesRepository.getAllBikes()
        } catch (t: Throwable) {
            // todo uncomment once "Could not resolve com.jakewharton.timber:timber:5.0.1." is fixed
//            Timber.e(t, "Exception while retrieving list of bikes")
            emptyList()
        }
    }
}
