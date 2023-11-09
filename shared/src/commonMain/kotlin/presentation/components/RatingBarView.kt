package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bn.store.kmp.MR
import dev.icerock.moko.resources.compose.painterResource


@Composable
fun RatingBarView(
    count: Int,
    title: String? = null,
    textStyle: TextStyle = TextStyle(),
    modifier: Modifier = Modifier
) {
    LazyRow(verticalAlignment = Alignment.CenterVertically) {
        items(5) {
            Image(
                painter = painterResource(if (it >= count) MR.images.inactive_star else MR.images.star),
                ""
            )

        }
        item {
            Gap(5.dp)
            title?.let {
                Text(
                    title, style = textStyle,
                )
            }
        }
    }
}