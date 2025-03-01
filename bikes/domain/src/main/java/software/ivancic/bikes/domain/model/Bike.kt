package software.ivancic.bikes.domain.model

data class Bike(
    val id: Int,
    val name: String,
    val isCurrentlyReserved: Boolean,
)
