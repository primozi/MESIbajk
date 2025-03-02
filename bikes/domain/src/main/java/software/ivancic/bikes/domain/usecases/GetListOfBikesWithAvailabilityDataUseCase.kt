package software.ivancic.bikes.domain.usecases

import org.koin.core.annotation.Factory
import software.ivancic.bikes.domain.BikesRepository
import software.ivancic.bikes.domain.model.BikeWithAvailabilityData
import software.ivancic.core.domain.CoroutineDispatchers
import software.ivancic.core.domain.UseCase

@Factory
class GetListOfBikesWithAvailabilityDataUseCase(
    private val bikesRepository: BikesRepository,
    coroutineDispatchers: CoroutineDispatchers,
) : UseCase<Unit, List<BikeWithAvailabilityData>>(coroutineDispatchers.io) {
    override suspend fun execute(parameters: Unit): List<BikeWithAvailabilityData> {
        return bikesRepository.getAllBikesWithAvailabilityData()
    }
}
