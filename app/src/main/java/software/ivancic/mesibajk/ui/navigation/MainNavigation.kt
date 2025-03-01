package software.ivancic.mesibajk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import software.ivancic.bikes.ui.main.MainBikesScreen

@Composable
fun MainNavigationScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.Bikes,
        modifier = modifier,
    ) {
        composable<Destinations.Bikes> {
            MainBikesScreen()
        }
    }
}
