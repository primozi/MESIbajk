package software.ivancic.bikes.data

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import software.ivancic.bikes.data.db.BikesDb
import software.ivancic.bikes.data.db.dao.BikeDao

@Module
@ComponentScan
class BikesDataDi

val bikesDataDi = module {
    single<BikesDb> {
        Room.databaseBuilder(
            androidContext(),
            BikesDb::class.java,
            "bikes_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    insertBikesData(db)
                }

                private fun insertBikesData(db: SupportSQLiteDatabase) {
                    InitialData.initialBikesData.forEach { bike ->
                        db.insert(
                            table = "bikes",
                            conflictAlgorithm = OnConflictStrategy.REPLACE,
                            values = ContentValues().apply {
                                put("id", bike.id)
                                put("name", bike.name)
                                put("code", bike.code)
                            }
                        )
                    }
                }
            })
            .build()
    }

    factory<BikeDao> {
        get<BikesDb>().getBikeDao()
    }
}
