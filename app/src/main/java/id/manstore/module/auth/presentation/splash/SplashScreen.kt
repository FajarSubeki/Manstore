package id.manstore.module.auth.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.manstore.R
import id.manstore.module.auth.util.Constants.SPLASH_SCREEN_DURATION
import id.manstore.module.destinations.HomeScreenDestination
import id.manstore.module.destinations.LoginScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Destination(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: SplashViewModel = hiltViewModel()
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val scale = remember {
            Animatable(0f)
        }

        val overshootInterpolator = remember {
            OvershootInterpolator(1.5f)
        }

        LaunchedEffect(key1 = true) {
            withContext(Dispatchers.Main) {
                scale.animateTo(
                    targetValue = 1.0f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = {
                            overshootInterpolator.getInterpolation(it)
                        }
                    )
                )

                delay(SPLASH_SCREEN_DURATION)

                val eventState = viewModel.eventState.value

                if (eventState) {
                    navigator.popBackStack()
                    navigator.navigate(HomeScreenDestination)
                } else {
                    navigator.popBackStack()
                    navigator.navigate(LoginScreenDestination)
                }
            }
        }

        Image(
            modifier = Modifier.padding(24.dp),
            painter = painterResource(id = R.drawable.mandiri_logo),
            contentDescription = null
        )
    }
}
