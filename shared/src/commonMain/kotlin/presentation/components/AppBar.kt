package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.theme.keyLine2


@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppBar(
    appBarBackground: Color = MaterialTheme.colors.primary,
    appBarContentColor: Color = MaterialTheme.colors.onPrimary,
    barTitle: String,
    leadingIcon: String? = null,
    trailingIcon: String? = null,
    onLeadingIconClicked: (() -> Unit)? = null,
    onTrailingIconClicked: (() -> Unit)? = null,
) {
    TopAppBar(
        backgroundColor = appBarBackground,
        contentColor = appBarContentColor,
        elevation = 0.dp,
        modifier = Modifier
            .background(color = appBarBackground)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        leadingIcon?.let {
            Icon(
                painter = painterResource(it),
                contentDescription = "leading icon of $barTitle",
                modifier = Modifier.clickable { onLeadingIconClicked?.let { clicked -> clicked() } }

            )
            Spacer(modifier = Modifier.size(keyLine2))
        }
        Text(
            modifier = Modifier,
            text = barTitle,
            style = MaterialTheme.typography.body1.copy(
                color = appBarContentColor,
                fontWeight = FontWeight.SemiBold
            )
        )

        trailingIcon?.let {
            Column(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(trailingIcon),
                    contentDescription = it.toString(),
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.End)
                        .clickable { onTrailingIconClicked?.let { clicked -> clicked() } }
                )
            }
        }
    }

}