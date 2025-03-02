package software.ivancic.bikes.domain.usecases

import org.koin.core.annotation.Factory
import software.ivancic.bikes.domain.BikesRepository
import software.ivancic.bikes.domain.model.ReservationData
import software.ivancic.core.domain.CoroutineDispatchers
import software.ivancic.core.domain.UseCase

@Factory
class SaveReservationDataUseCase(
    private val bikeRepository: BikesRepository,
    coroutineDispatchers: CoroutineDispatchers,
) : UseCase<ReservationData, Unit>(coroutineDispatchers.io) {

    override suspend fun execute(parameters: ReservationData) {
        bikeRepository.saveReservationData(parameters)
    }
}
