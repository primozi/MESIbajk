package software.ivancic.bikes.domain

import software.ivancic.bikes.domain.model.Bike

interface BikesRepository {
    suspend fun getAllBikes(): List<Bike>
}
