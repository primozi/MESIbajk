package software.ivancic.bikes.domain

import software.ivancic.bikes.domain.model.Bike
import software.ivancic.bikes.domain.model.BikeWithAvailabilityData
import software.ivancic.bikes.domain.model.ReservationData

interface BikesRepository {
    suspend fun getAllBikes(): List<Bike>
    suspend fun getAllBikesWithAvailabilityData(): List<BikeWithAvailabilityData>
    suspend fun saveReservationData(reservation: ReservationData)
}
