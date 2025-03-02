package software.ivancic.mesibajk

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.software_ivancic_bikes_data_BikesDataDi
import org.koin.ksp.generated.software_ivancic_bikes_domain_BikesDomainDi
import org.koin.ksp.generated.software_ivancic_bikes_ui_BikesUiDi
import org.koin.ksp.generated.software_ivancic_core_data_CoreDataDI
import org.koin.ksp.generated.software_ivancic_core_domain_CoreDomainDi
import software.ivancic.bikes.data.bikesDataDi
import software.ivancic.bikes.ui.bikesUiDi

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                software_ivancic_core_data_CoreDataDI,
                software_ivancic_core_domain_CoreDomainDi,

                software_ivancic_bikes_data_BikesDataDi,
                bikesDataDi,
                software_ivancic_bikes_domain_BikesDomainDi,
                software_ivancic_bikes_ui_BikesUiDi,
                bikesUiDi,
            )
        }
    }
}
