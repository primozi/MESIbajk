package software.ivancic.bikes.ui.addreservation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.koin.test.mock.declare
import software.ivancic.bikes.domain.BikesRepository
import software.ivancic.bikes.domain.model.Bike
import software.ivancic.bikes.domain.model.BikeDetails
import software.ivancic.bikes.domain.model.BikeWithAvailabilityData
import software.ivancic.bikes.domain.model.Department
import software.ivancic.bikes.domain.model.Intent
import software.ivancic.bikes.domain.model.ReservationData
import software.ivancic.bikes.domain.usecases.GetListOfBikesUseCase
import software.ivancic.bikes.domain.usecases.SaveReservationDataUseCase
import software.ivancic.core.domain.CoroutineDispatchers
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class AddReservationViewModelTest : KoinTest {

    private val viewModel: AddReservationViewModel by inject()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        startKoin {
            modules(
                module {
                    single<CoroutineDispatchers> {
                        val testScheduler = TestCoroutineScheduler()
                        object : CoroutineDispatchers {
                            override val main = StandardTestDispatcher(testScheduler)
                            override val io = StandardTestDispatcher(testScheduler)
                            override val default = StandardTestDispatcher(testScheduler)
                        }
                    }
                    factory<BikesRepository> {
                        object : BikesRepository {
                            override suspend fun getAllBikes(): List<Bike> = emptyList()
                            override suspend fun getBikeDetails(bikeId: Int): BikeDetails = TODO()
                            override suspend fun saveReservationData(reservation: ReservationData) {}
                            override suspend fun getAllBikesWithAvailabilityData(): List<BikeWithAvailabilityData> {
                                return emptyList()
                            }
                        }
                    }
                    factory { SaveReservationDataUseCase(get(), get()) }
                    factory { GetListOfBikesUseCase(get(), get()) }
                    single { AddReservationViewModel(get(), get()) }
                }
            )
        }
    }

    @After
    fun cleanup() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `when initializing view model, empty state is created`() {
        val state = viewModel.state.value

        assertEquals("", state.borrowerName)
        assertNull(state.borrowerNameError)
        assertNull(state.selectedBike)
        assertNull(state.bikeError)
        assertEquals(emptyList(), state.availableBikes)
        assertNull(state.selectedDepartment)
        assertEquals(Department.entries, state.availableDepartments)
        assertEquals(Intent.entries, state.availableIntents)
        assert(state.from > 0) // todo figure out how to test current timestamp
        assert(state.to > 0)
        assertEquals(0, state.approxDistanceInKm)
        assertNull(state.intent)
    }

    @Test
    fun `when add is clicked on an empty screen, error texts are set`() = runTest {
        assertNull(viewModel.state.value.borrowerNameError)
        val results: MutableList<AddReservationViewModel.State> = mutableListOf()

        viewModel.submitAction(AddReservationViewModel.Action.AddClicked)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.toList(results)
        }

        assertNotNull(results[0].borrowerNameError)
    }

    @Test
    fun `after error text is set, it is cleared when text is correct again`() = runTest {
        val results: MutableList<AddReservationViewModel.State> = mutableListOf()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.toList(results)
        }

        assertNull(results[0].borrowerNameError)

        viewModel.submitAction(AddReservationViewModel.Action.AddClicked)
        assertNotNull(results[1].borrowerNameError)

        viewModel.submitAction(AddReservationViewModel.Action.BorrowerNameChanged("valid name"))
        assertNotNull(results[2].borrowerNameError)
        assertEquals("valid name", results[2].borrowerName)

        viewModel.submitAction(AddReservationViewModel.Action.AddClicked)
        assertNull(results[3].borrowerNameError)
    }
}
