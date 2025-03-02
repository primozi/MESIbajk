package software.ivancic.bikes.ui

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import software.ivancic.bikes.ui.bikedetails.BikeDetailsViewModel

@Module
@ComponentScan
class BikesUiDi

val bikesUiDi = module {
    viewModel { params -> BikeDetailsViewModel(getBikeDetails = get(), bikeId = params.get()) }
}
