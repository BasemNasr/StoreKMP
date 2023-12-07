package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.Category
import presentation.theme.textColorSemiBlack


@Composable
fun CategoryCardTag(
    category: Category,
    onTap: (category: Category) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .padding(horizontal = 4.dp)
            .clip(CircleShape)
            .width(100.dp)
            .height(30.dp)
            .clickable {
                onTap(category)
            }
            .background(color = textColorSemiBlack),
        contentAlignment = Alignment.Center,
        content = {
            Text(
                textAlign = TextAlign.Center,
                text = category.name ?: "",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp
                )
            )
        })
}