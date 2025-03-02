package software.ivancic.bikes.data.db.entity

import com.jakewharton.fliptables.FlipTableConverters

data class DbBikeDetails(
    val name: String,
    val code: String,
    val latestReservationData: ReservationEntity?,
    val nextReservationData: ReservationEntity?,
    val kilometresRidden: Int,
    val reservationsCount: DbReservationsCount,
) {
    class DbReservationsCount {
        private val data = Array(DbDepartment.entries.size) {
            IntArray(DbIntent.entries.size) { 0 }
        }

        fun addData(department: DbDepartment, intent: DbIntent, count: Int): DbReservationsCount {
            data[department.ordinal][intent.ordinal] += count

            return this
        }

        fun getData(department: DbDepartment, intent: DbIntent): Int {
            return data[department.ordinal][intent.ordinal]
        }

        override fun toString(): String {
            val headers = DbIntent.entries
            val rows = DbDepartment.entries.map { department ->
                headers.map { intent ->
                    data[department.ordinal][intent.ordinal].toString()
                }.toTypedArray()
            }.toTypedArray()

            val headersStrings = arrayOf("/") + headers.map { it.name }.toTypedArray()
            val rowsStrings = rows.mapIndexed { index, strings ->
                arrayOf(DbDepartment.entries[index].name) + strings
            }.toTypedArray()
            return "\n" + FlipTableConverters.fromObjects(headersStrings, rowsStrings)
        }

        override fun equals(other: Any?): Boolean {
            if (other == null) {
                return false
            }

            if (other !is DbReservationsCount) {
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
