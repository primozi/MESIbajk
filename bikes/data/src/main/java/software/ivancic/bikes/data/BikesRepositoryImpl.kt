package software.ivancic.bikes.data

import org.koin.core.annotation.Factory
import software.ivancic.bikes.data.db.dao.BikeDao
import software.ivancic.bikes.domain.BikesRepository
import software.ivancic.bikes.domain.model.Bike

@Factory
class BikesRepositoryImpl(
    private val bikeDao: BikeDao,
) : BikesRepository {
    override suspend fun getAllBikes(): List<Bike> {
        return bikeDao.getAllBikes().map {
            Bike(
                id = it.id,
                name = it.name,
                isCurrentlyReserved = false, // todo
            )
        }
    }
}
