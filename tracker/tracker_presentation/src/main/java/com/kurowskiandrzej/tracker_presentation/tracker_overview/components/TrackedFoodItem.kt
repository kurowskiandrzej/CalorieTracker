package com.kurowskiandrzej.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.kurowskiandrzej.core_ui.LocalSpacing
import com.kurowskiandrzej.tracker_domain.model.TrackedFood
import com.kurowskiandrzej.core.R
import com.kurowskiandrzej.tracker_presentation.components.NutrientInfo

@Composable
fun TrackedFoodItem(
    trackedFood: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(spacing.spaceExtraSmall)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .padding(end = spacing.spaceSmall)
    ) {
        Row(
            modifier = Modifier.padding(spacing.spaceExtraSmall)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = trackedFood.imageUrl,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_burger)
                        fallback(R.drawable.ic_burger)
                    }
                ),
                contentDescription = trackedFood.name,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1.4f)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(
                            text = trackedFood.name,
                            style = MaterialTheme.typography.body1,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
                        Text(
                            text = stringResource(
                                id = R.string.nutrient_info,
                                trackedFood.amount,
                                trackedFood.calories
                            ),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.delete),
                modifier = Modifier
                    .clickable { onDeleteClick() }
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = spacing.spaceExtraSmall)
        ) {
            NutrientInfo(
                name = stringResource(id = R.string.carbs),
                amount = trackedFood.carbs,
                unit = stringResource(id = R.string.grams),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
            )
            Spacer(modifier = Modifier.width(spacing.spaceLarge))
            NutrientInfo(
                name = stringResource(id = R.string.protein),
                amount = trackedFood.protein,
                unit = stringResource(id = R.string.grams),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
            )
            Spacer(modifier = Modifier.width(spacing.spaceLarge))
            NutrientInfo(
                name = stringResource(id = R.string.fat),
                amount = trackedFood.fat,
                unit = stringResource(id = R.string.grams),
                amountTextSize = 16.sp,
                unitTextSize = 12.sp,
            )
        }

    }
}