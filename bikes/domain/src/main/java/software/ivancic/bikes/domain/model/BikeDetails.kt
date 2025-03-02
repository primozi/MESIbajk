package software.ivancic.bikes.domain.model

import com.jakewharton.fliptables.FlipTableConverters

data class BikeDetails(
    val name: String,
    val code: String,
    val latestReservationData: BasicReservationData?,
    val nextReservationData: BasicReservationData?,
    val kilometresRidden: Int,
    val reservationsCount: ReservationsCount,
) {
    data class BasicReservationData(
        val name: String,
        val from: Long,
        val to: Long,
    )

    class ReservationsCount {
        private val data = Array(Department.entries.size) {
            IntArray(Intent.entries.size) { 0 }
        }

        fun addData(department: Department, intent: Intent, count: Int) {
            data[department.ordinal][intent.ordinal] += count
        }

        fun getData(department: Department, intent: Intent): Int {
            return data[department.ordinal][intent.ordinal]
        }

        override fun toString(): String {
            val headers = Intent.entries
            val rows = Department.entries.map { department ->
                headers.map { intent ->
                    data[department.ordinal][intent.ordinal].toString()
                }.toTypedArray()
            }.toTypedArray()

            val headersStrings = arrayOf("/") + headers.map { it.name }.toTypedArray()
            val rowsStrings = rows.mapIndexed { index, strings ->
                arrayOf(Department.entries[index].name) + strings
            }.toTypedArray()
            return "\n" + FlipTableConverters.fromObjects(headersStrings, rowsStrings)
        }

        override fun equals(other: Any?): Boolean {
            if (other == null) {
                return false
            }

            if (other !is ReservationsCount) {
                return false
            }

            if (this === other) {
                return true
            }

            return data.contentDeepEquals(other.data)
        }

        override fun hashCode(): Int {
            return data.contentDeepHashCode()
        }
    }
}
