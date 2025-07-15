package com.teamsolply.solply.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.onboarding.OnBoardingState
import kotlinx.coroutines.delay

@Composable
fun StartingScreen(
    state: OnBoardingState,
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }

    val name = state.userNickname
    val compositionResult = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.finish_onboarding))
    val composition = compositionResult.value
    val progressState = animateLottieCompositionAsState(composition)
    val progress = progressState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 91.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${name}의 취향을 담았어요.",
                style = SolplyTheme.typography.display20Sb,
                color = SolplyTheme.colors.black
            )
            Text(
                text = "솔플리를 시작할게요!",
                style = SolplyTheme.typography.display20Sb,
                color = SolplyTheme.colors.black
            )
            LottieAnimation(
                composition = composition,
                progress = {progress},
                modifier = Modifier
                    .size(270.dp)
                    .padding(top = 72.dp)
            )
        }

        Image(
            painter = painterResource(R.drawable.img_box_graphic),
            contentDescription = "box-graphic",
            modifier = Modifier
                .fillMaxWidth()
                .height(212.dp)
                .align(Alignment.BottomCenter)
        )
    }
}
