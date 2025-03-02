package software.ivancic.bikes.domain.model

data class ReservationData(
    val bike: Bike,
    val start: Long,
    val end: Long,
    val usersName: String,
    val intent: Intent,
    val department: Department,
    val kilometres: Int,
)
