package software.ivancic.bikes.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bikes")
data class BikeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val code: String,
)
