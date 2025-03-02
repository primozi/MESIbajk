package software.ivancic.mesibajk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.compose.KoinContext
import software.ivancic.core.ui.theme.MESIbajkTheme
import software.ivancic.mesibajk.ui.navigation.MainNavigationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                KoinAndroidContext {
                    MESIbajkTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            MainNavigationScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
