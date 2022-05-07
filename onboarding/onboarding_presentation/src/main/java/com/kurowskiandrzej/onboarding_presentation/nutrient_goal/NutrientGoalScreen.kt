package com.kurowskiandrzej.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.kurowskiandrzej.core.R
import com.kurowskiandrzej.core.util.UiEvent
import com.kurowskiandrzej.core_ui.LocalSpacing
import com.kurowskiandrzej.onboarding_presentation.components.ActionButton
import com.kurowskiandrzej.onboarding_presentation.components.UnitTextField
import kotlinx.coroutines.flow.collect

@Composable
fun NutrientGoalScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: NutrientGoalViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            // Carbs
            UnitTextField(
                value = viewModel.state.carbsRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnCarbRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_carbs)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            // Protein
            UnitTextField(
                value = viewModel.state.proteinRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnProteinRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_proteins)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            // Fat
            UnitTextField(
                value = viewModel.state.fatRatio,
                onValueChange = {
                    viewModel.onEvent(NutrientGoalEvent.OnFatRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_fats)
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                viewModel.onEvent(NutrientGoalEvent.OnNextClick)
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}