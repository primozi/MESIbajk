package software.ivancic.bikes.domain.model

data class BikeWithAvailabilityData(
    val id: Int,
    val name: String,
    val code: String,
    val isReserved: Boolean,
)
