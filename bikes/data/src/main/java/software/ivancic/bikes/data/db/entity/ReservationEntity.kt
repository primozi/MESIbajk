package software.ivancic.bikes.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reservations",
    foreignKeys = [
        ForeignKey(
            entity = BikeEntity::class,
            parentColumns = ["id"],
            childColumns = ["bike_id"],
        )
    ],
    indices = [Index("bike_id")]
)
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "bike_id")
    val bikeId: Int,
    @ColumnInfo(name = "users_name")
    val usersName: String,
    @ColumnInfo(name = "start_timestamp")
    val startTimestamp: Long,
    @ColumnInfo(name = "end_timestamp")
    val endTimestamp: Long,
    val intent: String,
    val department: String,
    val kilometres: Int,
)
