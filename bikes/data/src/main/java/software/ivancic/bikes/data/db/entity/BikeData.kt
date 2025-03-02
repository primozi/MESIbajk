package software.ivancic.bikes.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class BikeData(
    @Embedded
    val bikeEntity: BikeEntity,
    @ColumnInfo(name = "is_reserved")
    val isReserved: Boolean,
)
