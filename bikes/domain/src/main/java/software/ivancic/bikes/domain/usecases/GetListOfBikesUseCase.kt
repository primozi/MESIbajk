package software.ivancic.bikes.domain.usecases

import org.koin.core.annotation.Factory
import software.ivancic.bikes.domain.BikesRepository
import software.ivancic.bikes.domain.model.Bike
import software.ivancic.core.domain.CoroutineDispatchers
import software.ivancic.core.domain.UseCase

@Factory
class GetListOfBikesUseCase(
    private val bikesRepository: BikesRepository,
    coroutineDispatchers: CoroutineDispatchers,
) : UseCase<Unit, List<Bike>>(coroutineDispatchers.io) {
    override suspend fun execute(parameters: Unit): List<Bike> {
        return bikesRepository.getAllBikes()
    }
}
