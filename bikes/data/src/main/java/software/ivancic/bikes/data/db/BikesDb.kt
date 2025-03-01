package software.ivancic.bikes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import software.ivancic.bikes.data.db.dao.BikeDao
import software.ivancic.bikes.data.db.entity.BikeEntity

@Database(
    entities = [BikeEntity::class],
    version = 1,
    exportSchema = true
)
abstract class BikesDb : RoomDatabase() {
    abstract fun getBikeDao(): BikeDao
}
