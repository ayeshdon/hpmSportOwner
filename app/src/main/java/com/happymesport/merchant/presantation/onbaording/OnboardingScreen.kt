package com.happymesport.merchant.presantation.onbaording

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.happymesport.merchant.domain.onboarding.OnboardingItemModel
import com.happymesport.merchant.presantation.DashboardActivity
import com.happymesport.merchant.presantation.event.AuthEvent
import com.happymesport.merchant.presantation.state.ViewState
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun onBoardingScreen(
    onFinished: () -> Unit,
    onEvent: (AuthEvent) -> Unit,
    state: State<ViewState<Boolean>>,
) {
    var context = LocalContext.current
    LaunchedEffect(Unit) {
        onEvent(AuthEvent.GetAuthFlag)
    }
    LaunchedEffect(state.value.data) {
        state.value.data?.let {
            Timber.e("ONBOARD LOGIN FLAG : ${it}")
            if (it) {
                var intent = Intent(context, DashboardActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }
    }

    val pages =
        listOf(
            OnboardingItemModel.FirstPage,
            OnboardingItemModel.SecondPage,
            OnboardingItemModel.ThirdPages,
        )

    val pagerState =
        rememberPagerState(initialPage = 0) {
            pages.size
        }

    Scaffold(bottomBar = {
    }, content = {
        Column(Modifier.padding(it).background(color = Color.White)) {
            bottomBar(pagerState, pages, onFinished)
            HorizontalPager(state = pagerState) { index ->
                OnboardingGraphUI(onboardingModel = pages[index])
            }
        }
    })
}

@Composable
fun bottomBar(
    pagerState: PagerState,
    pages: List<OnboardingItemModel>,
    onFinished: () -> Unit,
) {
    val buttonState =
        remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Start")
                    else -> listOf("", "")
                }
            }
        }

    val scope = rememberCoroutineScope()
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (buttonState.value[0].isNotEmpty()) {
                ButtonUi(
                    text = buttonState.value[0],
                    backgroundColor = Color.Transparent,
                    textColor = Color.Gray,
                ) {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            indicatorUI(pageSize = pages.size, currentPage = pagerState.currentPage)
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd,
        ) {
            ButtonUi(
                text = buttonState.value[1],
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                scope.launch {
                    if (pagerState.currentPage < pages.size - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        onFinished()
                    }
                }
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun OnboardingScreenPreview() {
//    onBoardingScreen(onEvent = {}, onFinished = {})
// }
