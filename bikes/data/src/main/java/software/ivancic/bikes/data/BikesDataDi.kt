package software.ivancic.bikes.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module
import software.ivancic.bikes.data.db.BikesDb
import software.ivancic.bikes.data.db.dao.BikeDao
import software.ivancic.core.domain.CoroutineDispatchers

@Module
@ComponentScan
class BikesDataDi

val bikesDataDi = module {
    single {
        androidContext().getSharedPreferences("bikes_prefs", Context.MODE_PRIVATE)
    }

    single<BikesDb> {
        Room.databaseBuilder(
            androidContext(),
            BikesDb::class.java,
            "bikes_database"
        ).build().also {
            val sharedPrefs = get<SharedPreferences>()
            val flagId = "initial_data_entered"

            if (!sharedPrefs.getBoolean(flagId, false)) {
                val coroutineDispatchers = get<CoroutineDispatchers>()
                CoroutineScope(coroutineDispatchers.io).launch {
                    it.getBikeDao().insert(initialBikesData)
                    sharedPrefs.edit().putBoolean(flagId, true).apply()
                }
            }
        }
    }

    factory<BikeDao> {
        get<BikesDb>().getBikeDao()
    }
}
