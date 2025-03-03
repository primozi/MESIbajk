package software.ivancic.bikes.data.db.dao

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import software.ivancic.bikes.data.db.entity.BikeData
import software.ivancic.bikes.data.db.entity.BikeEntity
import software.ivancic.bikes.data.db.entity.DbBikeDetails
import software.ivancic.bikes.data.db.entity.DbDepartment
import software.ivancic.bikes.data.db.entity.DbIntent
import software.ivancic.bikes.data.db.entity.ReservationEntity

@Dao
interface BikeDao {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Insert
    suspend fun saveBike(bikeEntity: BikeEntity)

    @Query(
        "SELECT b.*, " +
                "(CASE WHEN r.start_timestamp < :currentTimestamp " +
                "AND r.end_timestamp > :currentTimestamp " +
                "THEN 1 ELSE 0 END) AS is_reserved " +
                "FROM bikes b " +
                "LEFT JOIN reservations r ON b.id = r.bike_id"
    )
    suspend fun getAllBikesWithAvailabilityData(currentTimestamp: Long = System.currentTimeMillis()): List<BikeData>

    @Query("SELECT * FROM bikes")
    suspend fun getAllBikes(): List<BikeEntity>

    @Query("SELECT * FROM bikes WHERE id = :id")
    suspend fun getBike(id: Int): BikeEntity

    @Insert
    suspend fun saveReservationData(reservationEntity: ReservationEntity)

    /**
     * A bunch of methods, that are written to support this one, could be skipped if the
     * query had been written in a different, more complicated way.
     * I'm not a fan of complicating, so I'm keeping them here.
     */
    suspend fun getBikeDetails(id: Int): DbBikeDetails {
        val timestamp = System.currentTimeMillis()
        val bike = getBike(id)
        return DbBikeDetails(
            name = bike.name,
            code = bike.code,
            latestReservationData = getLatestReservationByBikeId(id, timestamp),
            nextReservationData = getNextReservationByBikeId(id, timestamp),
            kilometresRidden = getKilometresForBike(id, timestamp),
            reservationsCount = getReservationsCountByBikeId(id)
        )
    }

    suspend fun getReservationsCountByBikeId(bikeId: Int): DbBikeDetails.DbReservationsCount {
        val data = DbBikeDetails.DbReservationsCount()

        DbDepartment.entries.forEach { department ->
            DbIntent.entries.forEach { intent ->
                val count =
                    getReservationsCountByBikeIdDepartmentIntent(bikeId, department.key, intent.key)
                data.addData(department, intent, count)
            }
        }

        return data
    }

    @Query("SELECT COUNT(*) FROM reservations r WHERE r.bike_id = :bikeId AND r.department = :department AND r.intent = :intent")
    suspend fun getReservationsCountByBikeIdDepartmentIntent(
        bikeId: Int,
        department: String,
        intent: String,
    ): Int

    @Query(
        "SELECT SUM(r.kilometres) " +
                "FROM reservations r " +
                "WHERE r.bike_id = :id AND " +
                "r.end_timestamp <= :currentTimestamp"
    )
    suspend fun getKilometresForBike(
        id: Int,
        currentTimestamp: Long = System.currentTimeMillis(),
    ): Int

    @Query(
        "SELECT * " +
                "FROM reservations r " +
                "WHERE bike_id = :bikeId AND " +
                "r.start_timestamp <= :currentTimestamp " +
                "ORDER BY r.start_timestamp DESC " +
                "LIMIT 1"
    )
    suspend fun getLatestReservationByBikeId(
        bikeId: Int,
        currentTimestamp: Long = System.currentTimeMillis(),
    ): ReservationEntity?

    @Query(
        "SELECT * " +
                "FROM reservations r " +
                "WHERE r.bike_id = :bikeId AND " +
                "r.start_timestamp > :currentTimestamp " +
                "ORDER BY r.start_timestamp ASC " +
                "LIMIT 1"
    )
    suspend fun getNextReservationByBikeId(
        bikeId: Int,
        currentTimestamp: Long = System.currentTimeMillis(),
    ): ReservationEntity?
}
