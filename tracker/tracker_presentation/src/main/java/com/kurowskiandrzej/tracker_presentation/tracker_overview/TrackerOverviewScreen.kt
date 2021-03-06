package com.kurowskiandrzej.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kurowskiandrzej.core.util.UiEvent
import com.kurowskiandrzej.core_ui.LocalSpacing
import com.kurowskiandrzej.tracker_domain.model.MealType
import com.kurowskiandrzej.tracker_presentation.tracker_overview.components.*

@Composable
fun TrackerOverviewScreen(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
    viewModel: TrackerOverViewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverViewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverViewEvent.OnNextDayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = { viewModel.onEvent(TrackerOverViewEvent.OnToggleMealClick(meal)) },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        state.trackedFoods.forEach { food ->
                            if (meal.mealType == food.mealType) {
                                TrackedFoodItem(
                                    trackedFood = food,
                                    onDeleteClick = {
                                        viewModel.onEvent(
                                            TrackerOverViewEvent.OnDeleteTrackedFoodClick(food)
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            }
                        }
                        AddButton(
                            text = stringResource(id = MealType.getAddMealStringResource(meal.mealType.name)),
                            onClick = {
                                onNavigateToSearch(
                                    meal.name.asString(context),
                                    state.date.dayOfMonth,
                                    state.date.monthValue,
                                    state.date.year
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (meal.mealType != MealType.Snack && !meal.isExpanded) {
                Divider(
                    color = MaterialTheme.colors.onSurface,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(
                            horizontal = 30.dp,
                            vertical = 10.dp
                        )
                )
            }
        }
    }
}