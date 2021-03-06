package com.kurowskiandrzej.tracker_presentation.tracker_overview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kurowskiandrzej.core.R
import com.kurowskiandrzej.core_ui.LocalSpacing
import com.kurowskiandrzej.tracker_presentation.components.NutrientInfo
import com.kurowskiandrzej.tracker_presentation.components.UnitDisplay
import com.kurowskiandrzej.tracker_presentation.tracker_overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .clickable { onToggleClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = meal.drawableRes),
                    contentDescription = meal.name.asString(context),
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = meal.name.asString(context),
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier.alignByBaseline()
                        )
                        Row {
                            UnitDisplay(
                                amount = meal.calories,
                                unit = stringResource(id = R.string.kcal),
                                amountTextSize = 22.sp,
                                modifier = Modifier.alignByBaseline()
                            )
                            Spacer(modifier = Modifier.width(spacing.spaceSmall))
                            Icon(
                                imageVector = if (meal.isExpanded) {
                                    Icons.Default.KeyboardArrowUp
                                } else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (meal.isExpanded) {
                                    stringResource(id = R.string.collapse)
                                } else stringResource(id = R.string.extend)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Row {
                    NutrientInfo(
                        name = stringResource(id = R.string.carbs),
                        amount = meal.carbs,
                        unit = stringResource(id = R.string.grams)
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceLarge))
                    NutrientInfo(
                        name = stringResource(id = R.string.protein),
                        amount = meal.protein,
                        unit = stringResource(id = R.string.grams)
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceLarge))
                    NutrientInfo(
                        name = stringResource(id = R.string.fat),
                        amount = meal.fat,
                        unit = stringResource(id = R.string.grams)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}