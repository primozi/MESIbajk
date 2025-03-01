package software.ivancic.bikes.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import software.ivancic.bikes.ui.navigation.BikesNavigationScreen
import software.ivancic.core.ui.theme.MESIbajkTheme

@Composable
fun MainBikesScreen() {
    BikesNavigationScreen(
        modifier = Modifier
            .fillMaxSize()
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    MESIbajkTheme {
        MainBikesScreen()
    }
}
