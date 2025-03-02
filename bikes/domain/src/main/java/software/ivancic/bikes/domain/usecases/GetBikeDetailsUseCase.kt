package software.ivancic.bikes.domain.usecases

import org.koin.core.annotation.Factory
import software.ivancic.bikes.domain.BikesRepository
import software.ivancic.bikes.domain.model.BikeDetails
import software.ivancic.core.domain.CoroutineDispatchers
import software.ivancic.core.domain.UseCase

@Factory
class GetBikeDetailsUseCase(
    private val bikeRepository: BikesRepository,
    coroutineDispatchers: CoroutineDispatchers,
) : UseCase<Int, BikeDetails>(coroutineDispatchers.io) {

    override suspend fun execute(parameters: Int): BikeDetails {
        return bikeRepository.getBikeDetails(parameters)
    }
}
