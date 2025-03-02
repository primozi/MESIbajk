@file:OptIn(ExperimentalMaterial3Api::class)

package software.ivancic.mesibajk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Image(
                                                painter = painterResource(R.drawable.logo),
                                                contentDescription = null,
                                            )
                                        }
                                    },
                                )
                            },
                            content = { innerPadding ->
                                MainNavigationScreen(
                                    modifier = Modifier
                                        .padding(innerPadding)
                                )
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}
