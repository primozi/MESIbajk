package software.ivancic.bikes.data

import org.koin.core.annotation.Factory
import software.ivancic.bikes.data.db.dao.BikeDao
import software.ivancic.bikes.data.db.entity.DbDepartment
import software.ivancic.bikes.data.db.entity.DbIntent
import software.ivancic.bikes.data.db.entity.ReservationEntity
import software.ivancic.bikes.domain.BikesRepository
import software.ivancic.bikes.domain.model.Bike
import software.ivancic.bikes.domain.model.BikeWithAvailabilityData
import software.ivancic.bikes.domain.model.Department
import software.ivancic.bikes.domain.model.Intent
import software.ivancic.bikes.domain.model.ReservationData

@Factory
class BikesRepositoryImpl(
    private val bikeDao: BikeDao,
) : BikesRepository {
    override suspend fun getAllBikes(): List<Bike> {
        return bikeDao.getAllBikes().map {
            Bike(
                id = it.id,
                name = it.name,
            )
        }
    }

    override suspend fun getAllBikesWithAvailabilityData(): List<BikeWithAvailabilityData> {
        return bikeDao.getAllBikesWithAvailabilityData().map {
            BikeWithAvailabilityData(
                id = it.bikeEntity.id,
                name = it.bikeEntity.name,
                code = it.bikeEntity.code,
                isCurrentlyReserved = it.isReserved,
            )
        }
    }

    override suspend fun saveReservationData(reservation: ReservationData) {
        bikeDao.saveReservationData(
            ReservationEntity(
                usersName = reservation.usersName,
                startTimestamp = reservation.start,
                endTimestamp = reservation.end,
                intent = when (reservation.intent) {
                    Intent.PRIVATE -> DbIntent.PRIVATE.key
                    Intent.BUSINESS -> DbIntent.BUSINESS.key
                },
                bikeId = reservation.bike.id,
                department = when (reservation.department) {
                    Department.DEVELOPMENT -> DbDepartment.DEVELOPMENT.key
                    Department.SALES -> DbDepartment.SALES.key
                    Department.MARKETING -> DbDepartment.MARKETING.key
                    Department.PRODUCTION -> DbDepartment.PRODUCTION.key
                },
                kilometres = reservation.kilometres,
            )
        )
    }
}
