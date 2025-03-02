package software.ivancic.bikes.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import software.ivancic.bikes.data.db.BikesDb
import software.ivancic.bikes.data.db.dao.BikeDao
import software.ivancic.bikes.data.db.entity.BikeEntity
import software.ivancic.bikes.data.db.entity.DbBikeDetails
import software.ivancic.bikes.data.db.entity.DbDepartment
import software.ivancic.bikes.data.db.entity.DbIntent
import software.ivancic.bikes.data.db.entity.ReservationEntity
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReservationsDbTests {

    private lateinit var db: BikesDb
    private lateinit var bikeDao: BikeDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BikesDb::class.java
        ).build()
        bikeDao = db.getBikeDao()
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        db.close()
    }

    // region latest and next reservation
    @Test
    fun givenReservationInPastAndFuture_getLatestReservation_success() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike = BikeEntity(0, "bikeName", "bikeCode")
        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike.id, "name0", timestamp - 20, timestamp - 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike.id, "name1", timestamp - 10, timestamp + 0, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike.id, "name2", timestamp - 0, timestamp + 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike.id, "name3", timestamp + 10, timestamp + 20, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val actual = bikeDao.getLatestReservationByBikeId(bike.id, timestamp)

        assertNotNull(actual)
        assertEquals(3, actual!!.id)
    }

    @Test
    fun givenReservationInPastAndFuture_getNextReservation_success() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike = BikeEntity(0, "bikeName", "bikeCode")
        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike.id, "name0", timestamp - 20, timestamp - 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike.id, "name1", timestamp - 10, timestamp + 0, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike.id, "name2", timestamp - 0, timestamp + 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike.id, "name3", timestamp + 10, timestamp + 20, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val actual = bikeDao.getNextReservationByBikeId(bike.id, timestamp)

        assertNotNull(actual)
        assertEquals(4, actual!!.id)
    }

    @Test
    fun givenReservationInPastOnly_getLatestReservation_success() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike = BikeEntity(0, "bikeName", "bikeCode")
        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike.id, "name0", timestamp - 50, timestamp - 40, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike.id, "name1", timestamp - 40, timestamp - 30, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike.id, "name2", timestamp - 30, timestamp - 20, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike.id, "name3", timestamp - 20, timestamp - 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val actual = bikeDao.getLatestReservationByBikeId(bike.id, timestamp)

        assertNotNull(actual)
        assertEquals(4, actual!!.id)
    }

    @Test
    fun givenReservationInFutureOnly_getNextReservation_success() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike = BikeEntity(0, "bikeName", "bikeCode")
        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike.id, "name1", timestamp + 10, timestamp + 20, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike.id, "name2", timestamp + 20, timestamp + 30, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike.id, "name3", timestamp + 30, timestamp + 40, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike.id, "name4", timestamp + 40, timestamp + 50, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val actual = bikeDao.getNextReservationByBikeId(bike.id, timestamp)

        assertNotNull(actual)
        assertEquals(1, actual!!.id)
    }

    @Test
    fun givenReservationInPastOnly_getNextReservation_null() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike = BikeEntity(0, "bikeName", "bikeCode")
        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike.id, "name0", timestamp - 50, timestamp - 40, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike.id, "name1", timestamp - 40, timestamp - 30, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike.id, "name2", timestamp - 30, timestamp - 20, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike.id, "name3", timestamp - 20, timestamp - 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val actual = bikeDao.getNextReservationByBikeId(bike.id, timestamp)

        assertNull(actual)
    }

    @Test
    fun givenReservationInFutureOnly_getLatestReservation_null() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike = BikeEntity(0, "bikeName", "bikeCode")
        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike.id, "name1", timestamp + 10, timestamp + 20, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike.id, "name2", timestamp + 20, timestamp + 30, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike.id, "name3", timestamp + 30, timestamp + 40, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike.id, "name4", timestamp + 40, timestamp + 50, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val actual = bikeDao.getLatestReservationByBikeId(bike.id, timestamp)

        assertNull(actual)
    }

    // endregion latest and next reservation

    // region kilometres aggregation

    @Test
    fun givenNoEntries_getKilometresAggregation_returnZero() = runTest {

        val actual = bikeDao.getKilometresForBike(0)

        assertNotNull(actual)
        assertEquals(0, actual)
    }

    @Test
    fun givenMultipleEntries_getKilometresAggregation_returnSum() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike1 = BikeEntity(0, "bikeName1", "bikeCode1")
        val bike2 = BikeEntity(1, "bikeName2", "bikeCode2")
        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike1.id, "name0", timestamp - 20, timestamp - 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike1.id, "name1", timestamp - 10, timestamp + 0, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike1.id, "name2", timestamp - 0, timestamp + 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike1.id, "name3", timestamp + 10, timestamp + 20, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(5, bike2.id, "name20", timestamp - 20, timestamp - 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike1)
        bikeDao.saveBike(bike2)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val actual = bikeDao.getKilometresForBike(bike1.id, timestamp)

        assertNotNull(actual)
        assertEquals(2, actual)
    }

    // endregion kilometres aggregation

    // region number of reservations aggregation

    @Test
    fun givenNoEntries_getReservationsCount_returnZero() = runTest {

        val expected = DbBikeDetails.DbReservationsCount()
        val actual = bikeDao.getReservationsCountByBikeId(0)

        assertNotNull(actual)

        assertEquals(expected, actual)
    }

    @Test
    fun givenMultipleEntries_getReservationsCount_returnSum() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike = BikeEntity(0, "bikeName1", "bikeCode1")

        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike.id, "name1", timestamp - 20, timestamp - 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike.id, "name2", timestamp - 20, timestamp - 10, DbIntent.BUSINESS.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike.id, "name3", timestamp - 20, timestamp - 10, DbIntent.BUSINESS.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike.id, "name4", timestamp - 10, timestamp + 0, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(5, bike.id, "name5", timestamp - 10, timestamp + 0, DbIntent.BUSINESS.key, DbDepartment.SALES.key, 1),
            ReservationEntity(6, bike.id, "name6", timestamp - 0, timestamp + 10, DbIntent.PRIVATE.key, DbDepartment.MARKETING.key, 1),
            ReservationEntity(7, bike.id, "name7", timestamp - 0, timestamp + 10, DbIntent.BUSINESS.key, DbDepartment.MARKETING.key, 1),
            ReservationEntity(8, bike.id, "name8", timestamp - 0, timestamp + 10, DbIntent.BUSINESS.key, DbDepartment.MARKETING.key, 1),
            ReservationEntity(9, bike.id, "name9", timestamp - 0, timestamp + 10, DbIntent.BUSINESS.key, DbDepartment.MARKETING.key, 1),
           ReservationEntity(10, bike.id, "name10", timestamp + 10, timestamp + 20, DbIntent.PRIVATE.key, DbDepartment.PRODUCTION.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val expected = DbBikeDetails.DbReservationsCount()
            .addData(DbDepartment.SALES, DbIntent.PRIVATE, 2)
            .addData(DbDepartment.SALES, DbIntent.BUSINESS, 3)
            .addData(DbDepartment.MARKETING, DbIntent.PRIVATE, 1)
            .addData(DbDepartment.MARKETING, DbIntent.BUSINESS, 3)
            .addData(DbDepartment.PRODUCTION, DbIntent.PRIVATE, 1)
        val actual = bikeDao.getReservationsCountByBikeId(bike.id)

        assertNotNull(actual)

        assertEquals(expected, actual)
    }

    @Test
    fun nonExistentBikeId_getReservationsCount_returnSum() = runTest {
        val timestamp = System.currentTimeMillis()
        val bike = BikeEntity(0, "bikeName1", "bikeCode1")

        val reservations = listOf(
            // @formatter:off
            ReservationEntity(1, bike.id, "name1", timestamp - 20, timestamp - 10, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(2, bike.id, "name2", timestamp - 20, timestamp - 10, DbIntent.BUSINESS.key, DbDepartment.SALES.key, 1),
            ReservationEntity(3, bike.id, "name3", timestamp - 20, timestamp - 10, DbIntent.BUSINESS.key, DbDepartment.SALES.key, 1),
            ReservationEntity(4, bike.id, "name4", timestamp - 10, timestamp + 0, DbIntent.PRIVATE.key, DbDepartment.SALES.key, 1),
            ReservationEntity(5, bike.id, "name5", timestamp - 10, timestamp + 0, DbIntent.BUSINESS.key, DbDepartment.SALES.key, 1),
            ReservationEntity(6, bike.id, "name6", timestamp - 0, timestamp + 10, DbIntent.PRIVATE.key, DbDepartment.MARKETING.key, 1),
            ReservationEntity(7, bike.id, "name7", timestamp - 0, timestamp + 10, DbIntent.BUSINESS.key, DbDepartment.MARKETING.key, 1),
            ReservationEntity(8, bike.id, "name8", timestamp - 0, timestamp + 10, DbIntent.BUSINESS.key, DbDepartment.MARKETING.key, 1),
            ReservationEntity(9, bike.id, "name9", timestamp - 0, timestamp + 10, DbIntent.BUSINESS.key, DbDepartment.MARKETING.key, 1),
            ReservationEntity(10, bike.id, "name10", timestamp + 10, timestamp + 20, DbIntent.PRIVATE.key, DbDepartment.PRODUCTION.key, 1),
            // @formatter:on
        )
        bikeDao.saveBike(bike)
        reservations.forEach {
            bikeDao.saveReservationData(it)
        }

        val expected = DbBikeDetails.DbReservationsCount()
        val actual = bikeDao.getReservationsCountByBikeId(1)

        assertNotNull(actual)

        assertEquals(expected, actual)
    }

    // endregion number of reservations aggregation
}
