package software.ivancic.bikes.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import software.ivancic.bikes.data.db.entity.BikeEntity

@Dao
interface BikeDao {
    @Insert
    suspend fun insert(bikes: List<BikeEntity>)

    @Query("SELECT * FROM bikes")
    suspend fun getAllBikes(): List<BikeEntity>
}
