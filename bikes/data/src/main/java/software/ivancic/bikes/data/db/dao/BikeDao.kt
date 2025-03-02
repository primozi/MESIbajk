package software.ivancic.bikes.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import software.ivancic.bikes.data.db.entity.BikeData
import software.ivancic.bikes.data.db.entity.BikeEntity
import software.ivancic.bikes.data.db.entity.ReservationEntity

@Dao
interface BikeDao {

    @Query("SELECT b.*, " +
            "(CASE WHEN r.start_timestamp < datetime(current_timestamp, 'localtime') " +
            "AND r.end_timestamp > datetime(current_timestamp, 'localtime') " +
            "THEN 1 ELSE 0 END) AS is_reserved " +
            "FROM bikes b " +
            "LEFT JOIN reservations r ON b.id = r.bike_id")
    suspend fun getAllBikesWithAvailabilityData(): List<BikeData>

    @Query("SELECT * FROM bikes")
    suspend fun getAllBikes(): List<BikeEntity>

    @Insert
    suspend fun saveReservationData(reservationEntity: ReservationEntity)
}
