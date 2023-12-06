package presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.theme.BorderColor

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileSectionCard(
    icon: @Composable () -> Unit,
    title: String,
    color: Color = Color.Black,
    withLine: Boolean = true,
    onClicked: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .clickable {
                    onClicked()
                }
                .testTag(title)
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = color,
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource("arrow_right.xml"),
                contentDescription = title,
                contentScale = ContentScale.None
            )

        }
        if (withLine) {
            Box(
                modifier = Modifier
                    .padding(0.dp)
                    .width(345.dp)
                    .height(1.dp)
                    .background(color = BorderColor)
            )
        }
    }
}